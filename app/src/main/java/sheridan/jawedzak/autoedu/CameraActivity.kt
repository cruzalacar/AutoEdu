package sheridan.jawedzak.autoedu

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.android.synthetic.main.fragment_search.*

import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel
import sheridan.jawedzak.autoedu.ml.MobilenetV110224Quant
import sheridan.jawedzak.autoedu.ml.SymbolModel
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraActivity : AppCompatActivity() {

    var list = ArrayList<DatabaseModel>()

    //initialize variables
    lateinit var select_image_button : Button
    lateinit var make_prediction : Button
    lateinit var img_view : ImageView
    lateinit var text_view : TextView
    lateinit var result_text: TextView
    lateinit var bitmap: Bitmap
    lateinit var seatbelt_button : Button
    lateinit var interpreter: Interpreter
    private lateinit var reference: DatabaseReference

    val CAMERA_REQUEST_CODE = 100
    val GALLERY_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

        val conditions = CustomModelDownloadConditions.Builder()
                .requireWifi()  // Also possible: .requireCharging() and .requireDeviceIdle()
                .build()

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

        //initialize buttons
        select_image_button = findViewById(R.id.button)
        make_prediction = findViewById(R.id.button2)
        img_view = findViewById(R.id.imageView2)
        text_view = findViewById(R.id.textView)
        seatbelt_button = findViewById(R.id.seatbelt_btn)
        result_text = findViewById(R.id.label_text)


        //label assets
//        val labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        //select image button handler
//        select_image_button.setOnClickListener(View.OnClickListener {
//            showCameraDialog()
//        })


        //scan image method
        make_prediction.setOnClickListener(View.OnClickListener {
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

            val reader = BufferedReader(InputStreamReader(assets.open("labels.txt")))
            for (i in 0..highestIndex) {
                iconLabel = reader.readLine()

            }
            val resText = "Image identified as ${iconLabel}"
            result_text.text = resText






        })
        showCameraDialog()
    }


    /*private fun getData() {
        //pulling data from firebase
        reference.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.e("cancel", p0.toString())
            }

            //calling recycler view to access symbols
            override fun onDataChange(p0: DataSnapshot) {
                for (data in p0.children) {
                    var model = data.getValue(DatabaseModel::class.java)
                    list.add(model as DatabaseModel)
                }
            }
        })
    }*/

    private fun showCameraDialog(){
        val alertDialog = AlertDialog.Builder(this)
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
                Log.d("nothing ","test")
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST_CODE) {
            img_view.setImageBitmap(data?.extras?.get("data") as Bitmap)
            bitmap = data?.extras?.get("data") as Bitmap

        } else {
            //retrieve image from user
            img_view.setImageURI(data?.data)

            //image media store
            var uri : Uri ?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }

    }

    //max input on camera functionality
    fun getMax(arr:FloatArray) : Int{
        var ind = 0;
        var min = 0.0f;

        //camera timer
        for(i in 0..1000)
        {
            //length of photo
            if(arr[i] > min)
            {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }
}