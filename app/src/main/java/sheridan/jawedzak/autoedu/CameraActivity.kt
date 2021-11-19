package sheridan.jawedzak.autoedu

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ml.modeldownloader.CustomModel
import com.google.firebase.ml.modeldownloader.CustomModelDownloadConditions
import com.google.firebase.ml.modeldownloader.DownloadType
import com.google.firebase.ml.modeldownloader.FirebaseModelDownloader
import kotlinx.android.synthetic.main.fragment_search.*

import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.image.TensorImage
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera)

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
        select_image_button.setOnClickListener(View.OnClickListener {
            Log.d("mssg", "button pressed")
            var intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"

            //retrieve result to next page
            startActivityForResult(intent, 100)
        })

        //scan image method
        make_prediction.setOnClickListener(View.OnClickListener {
            //download tf model


            var resizedBitmap = Bitmap.createScaledBitmap(bitmap, 180, 180, true)
            val input = ByteBuffer.allocateDirect(180*180*3*4).order(ByteOrder.nativeOrder())
            for (y in 0 until 180) {
                for (x in 0 until 180) {
                    val px = resizedBitmap.getPixel(x, y)

                    // Get channel values from the pixel value.
                    val r = Color.red(px)
                    val g = Color.green(px)
                    val b = Color.blue(px)

                    // Normalize channel values to [-1.0, 1.0]. This requirement depends on the model.
                    // For example, some models might require values to be normalized to the range
                    // [0.0, 1.0] instead.
                    val rf = (r - 127) / 255f
                    val gf = (g - 127) / 255f
                    val bf = (b - 127) / 255f

                    input.putFloat(rf)
                    input.putFloat(gf)
                    input.putFloat(bf)
                }
            }
            val bufferSize = 19 * java.lang.Float.SIZE / java.lang.Byte.SIZE
            val modelOutput = ByteBuffer.allocateDirect(bufferSize).order(ByteOrder.nativeOrder())
            interpreter?.run(input, modelOutput)

            var labelTxt: String = ""
            var confidence: Float = 0.0F

            modelOutput.rewind()
            val probabilities = modelOutput.asFloatBuffer()
            try {
                val reader = BufferedReader(
                    InputStreamReader(assets.open("labels.txt"))
                )
                        for (i in 0 until (probabilities.capacity() - 1)) {
                            val label: String = reader.readLine()
                            val probability = probabilities.get(i)
                            println("$label: $probability")

                            if (probability > confidence) {
                                labelTxt = label
                                confidence = probability
                            }
                        }
            } catch (e: IOException) {
                // File not found?
            }

            result_text.text = "${labelTxt} with a confidence of ${confidence}"



////            var model = MobilenetV110224Quant.newInstance(this)
//            var model = SymbolModel.newInstance(this)
//            var tbuffer = TensorImage.fromBitmap(resized)
//            var byteBuffer = tbuffer.buffer
//
//            // Creates inputs for reference.
//            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 180, 180, 3), DataType.UINT8)
//            inputFeature0.loadBuffer(byteBuffer)
//
//            // Runs model inference and gets result.
//            val outputs = model.process(inputFeature0)
//            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//            //label image
//            var max = getMax(outputFeature0.floatArray)
//            text_view.setText(labels[max])

            // Releases model resources if no longer used.

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