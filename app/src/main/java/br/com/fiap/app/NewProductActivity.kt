package br.com.fiap.app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_new_product.*
import android.util.Log
import android.widget.Toast
import br.com.fiap.app.model.Product
import br.com.fiap.app.utils.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NewProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_product)

        btSave.setOnClickListener{
            var product = Product()

            product.name = etProductName.text.toString()

            saveProduct(product)

            val data = Intent()
            setResult(RESULT_OK, data)
            finish()
        }
    }

    private fun saveProduct(product: Product) {
        val call = RetrofitBuilder().productService().addProduct(product = product )
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@NewProductActivity,
                        getString(R.string.product_created),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.e("ERROR: ", t.message)
            }
        })
    }
}
