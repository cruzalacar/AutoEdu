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

import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import sheridan.jawedzak.autoedu.ml.MobilenetV110224Quant

class CameraActivity : AppCompatActivity() {

    //initialize variables
    //testing commit
    //commit
    lateinit var select_image_button : Button
    lateinit var make_prediction : Button
    lateinit var img_view : ImageView
    lateinit var text_view : TextView
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

        //initialize buttons
        select_image_button = findViewById(R.id.button)
        make_prediction = findViewById(R.id.button2)
        img_view = findViewById(R.id.imageView2)
        text_view = findViewById(R.id.textView)

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

        //scan image method
        make_prediction.setOnClickListener(View.OnClickListener {
            var resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
            val model = MobilenetV110224Quant.newInstance(this)
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