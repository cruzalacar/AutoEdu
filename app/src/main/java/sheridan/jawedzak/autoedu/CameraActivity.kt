package sheridan.jawedzak.autoedu

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_search.*

import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import sheridan.jawedzak.autoedu.dashLightSymbols.DataAdapter
import sheridan.jawedzak.autoedu.dashLightSymbols.DatabaseModel
import sheridan.jawedzak.autoedu.dashLightSymbols.SymbolDetail
import sheridan.jawedzak.autoedu.ml.MobilenetV110224Quant
import sheridan.jawedzak.autoedu.ml.SymbolModel

class CameraActivity : AppCompatActivity() {

    var list = ArrayList<DatabaseModel>()

    //initialize variables
    lateinit var select_image_button : Button
    lateinit var make_prediction : Button
    lateinit var img_view : ImageView
    lateinit var text_view : TextView
    lateinit var bitmap: Bitmap
    lateinit var seatbelt_button : Button
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

        //initialize buttons
        select_image_button = findViewById(R.id.button)
        make_prediction = findViewById(R.id.button2)
        img_view = findViewById(R.id.imageView2)
        text_view = findViewById(R.id.textView)
        seatbelt_button = findViewById(R.id.seatbelt_btn)



        //label assets
        val labels = application.assets.open("labels.txt").bufferedReader().use { it.readText() }.split("\n")

        //select image button handler
        select_image_button.setOnClickListener(View.OnClickListener {
            Log.d("mssg", "button pressed")
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            //retrieve result to next page
            startActivityForResult(intent, 100)
        })

        seatbelt_button.setOnClickListener {
            var intent = Intent(this@CameraActivity, SeatBeltActivity::class.java)

            startActivity(intent)
        }

        //scan image method
        make_prediction.setOnClickListener(View.OnClickListener {
            var resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
//            val model = MobilenetV110224Quant.newInstance(this)
            var model = SymbolModel.newInstance(this)
            var tbuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tbuffer.buffer

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.UINT8)
            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            //label image
            var max = getMax(outputFeature0.floatArray)
            text_view.setText(labels[max])




            // Releases model resources if no longer used.
            model.close()


        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //retrieve image from user
        img_view.setImageURI(data?.data)

        //image media store
        var uri : Uri ?= data?.data
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
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