package sheridan.jawedzak.autoedu.bottomNavbar

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor.open
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import sheridan.jawedzak.autoedu.CameraActivity
import sheridan.jawedzak.autoedu.R
import sheridan.jawedzak.autoedu.dashLightSymbols.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.nio.channels.AsynchronousFileChannel.open


class HomeFragment : Fragment(), OnSymbolClickListener {

    //array list database
    var list = ArrayList<DatabaseModel>()
    var filteredList = ArrayList<DatabaseModel>()

    //initialize variable
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: HomeSymbolAdapter
    private lateinit var tutorialAdapter: TutorialSymbolAdapter

    lateinit var interpreter: Interpreter
    lateinit var bitmap: Bitmap

    val CAMERA_REQUEST_CODE = 100
    val GALLERY_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //initialize database and symbols
        database = FirebaseDatabase.getInstance()
        reference = database.getReference("Symbols")

        //retrieve data
        getData()
        val conditions = CustomModelDownloadConditions.Builder().requireWifi().build()

        FirebaseModelDownloader.getInstance()
                .getModel("dashsymbol_model", DownloadType.LOCAL_MODEL_UPDATE_IN_BACKGROUND,
                        conditions)
                .addOnSuccessListener { model: CustomModel? ->
                    // Download complete. Depending on your app, you could enable the ML
                    // feature, or switch from the local model to the remote model, etc.
                    print("hit")

                    // The CustomModel object contains the local path of the model file,
                    // which you can use to instantiate a TensorFlow Lite interpreter.
                    val modelFile = model?.file
                    if (modelFile != null) {
                        interpreter = Interpreter(modelFile)
                    }
                }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Activate camera button when clicked
        ic_camera.setOnClickListener{
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.apply {
                setTitle("Image Selection")
                setMessage("Please select the method to select the image")
                setPositiveButton("Camera"){ _, _ ->
                    val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    try {
                        startActivityForResult(pictureIntent, CAMERA_REQUEST_CODE)
                    } catch(e: ActivityNotFoundException){
                        Log.e("Exception", e.toString())
                    }
                }
                setNegativeButton("Phone Gallery"){ _, _ ->
                    Log.d("mssg", "button pressed")
                    var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"

                    //retrieve result to next page
                    startActivityForResult(intent, GALLERY_REQUEST_CODE)
                }
                setNeutralButton("Cancel"){ _, _ ->

                }
            }.create().show()
        }
    }

    override fun onSymbolItemClicked(position: Int) {
        //retrieving symbol information from database
        var intent = Intent(activity, SymbolDetail::class.java)
        intent.putExtra("name", filteredList[position].name)
        intent.putExtra("trigger", filteredList[position].trigger)
        intent.putExtra("description", filteredList[position].description)
        intent.putExtra("solution", filteredList[position].solution)
        intent.putExtra("icon", filteredList[position].icon)
        intent.putExtra("steps", filteredList[position].steps)

        //open activity
        startActivity(intent)
    }

    override fun onShowTutorial(position: Int) {
        var symbol = filteredList[position]
        var intent = Intent(activity, SymbolFix::class.java)
        intent.putExtra("steps", symbol.steps)
        intent.putExtra("name", symbol.name)
        startActivity(intent)
    }

    private fun getData(){
        //pulling data from firebase
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("cancel", p0.toString())
            }

            //calling recycler view to access symbols
            override fun onDataChange(p0: DataSnapshot) {
                list.clear()
                for (data in p0.children) {
                    Log.e("data", data.toString())
                    //retrieve list of symbols from database
                    var model = data.getValue(DatabaseModel::class.java)
                    //list of symbols
                    list.add(model as DatabaseModel)
                }

                var filteredItems = ArrayList<DatabaseModel>()

                for (item in list){
                    if (item.common) {
                        filteredItems.add(item)
                    }
                }

                filteredList = filteredItems
                // Filter out the list only by those that are listed as "common"

                //list of symbols
                if (list.size > 0) {
                    adapter = HomeSymbolAdapter(filteredList, this@HomeFragment)
                    tutorialAdapter = TutorialSymbolAdapter(filteredList, this@HomeFragment)
                    home_recyclerview.adapter = adapter
                    tutorial_recyclerview.adapter = tutorialAdapter
                    //horizontal layout view rotation
                    home_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                    tutorial_recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE) {
            bitmap = data?.extras?.get("data") as Bitmap
            processImage()
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            //image media store
            var uri : Uri ?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, uri)
            processImage()
        }
    }

    private fun processImage(){
        val imageProcessor = ImageProcessor.Builder()
                .add(ResizeOp(180, 180, ResizeOp.ResizeMethod.BILINEAR)) //.add(new NormalizeOp(127.5f, 127.5f))
                .build()

        var tImage = TensorImage(DataType.FLOAT32)

        tImage.load(bitmap)
        tImage = imageProcessor.process(tImage)

        val probabilityBuffer = TensorBuffer.createFixedSize(intArrayOf(1, 19), DataType.FLOAT32)
        interpreter.run(tImage.buffer, probabilityBuffer.buffer)

        Log.i("RESULT", probabilityBuffer.floatArray.contentToString())

        var highestIndex = 0
        val bufferArray = probabilityBuffer.floatArray

        for (i in 0..bufferArray.size - 1){
            if (bufferArray[i] > bufferArray[highestIndex]){
                highestIndex = i
            }
        }

        var iconLabel: String = ""

        val reader = BufferedReader(InputStreamReader(context?.assets?.open("labels.txt")))
        for (i in 0..highestIndex) {
            iconLabel = reader.readLine()

        }
        val resText = "Image identified as ${iconLabel}"
        Log.i("Image prediction",iconLabel)

        openSymbolDetails(iconLabel)


    }

    private fun openSymbolDetails(symbolLabel: String){
        var itemIndex = -1
        for (i in 0 until list.size){
            if (list[i].name.equals(symbolLabel)){
                itemIndex = i
                break
            }
        }

        if (itemIndex != -1){
            var intent = Intent(activity, SymbolDetail::class.java)
            intent.putExtra("name", list[itemIndex].name)
            intent.putExtra("trigger", list[itemIndex].trigger)
            intent.putExtra("description", list[itemIndex].description)
            intent.putExtra("solution",list[itemIndex].solution)
            intent.putExtra("icon", list[itemIndex].icon)
            intent.putExtra("steps", list[itemIndex].steps)

            startActivity(intent)
        } else {
            val alertDialog = AlertDialog.Builder(requireContext())
            alertDialog.apply {
                setTitle("Error")
                setMessage("Unable to find details for the symbol \"${symbolLabel}\". ")
                setPositiveButton("ok") { _, _ ->
                }
            }.create().show()
        }
    }

    //recycler view to display top 10 indicators
    private fun getCommonIndicators() {

    }

    //recycler view to display top 5 tutorials
    private fun getCommonTutorials() {

    }


}