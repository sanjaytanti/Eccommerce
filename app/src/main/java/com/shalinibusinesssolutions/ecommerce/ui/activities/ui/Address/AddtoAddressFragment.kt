package com.shalinibusinesssolutions.ecommerce.ui.activities.ui.Address

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentAddtoAddressBinding
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.AddToCartResponse
import com.shalinibusinesssolutions.ecommerce.ui.utility.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddtoAddressFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentAddtoAddressBinding
    var  subtotal=0.0f
    var DeliveryCharge = ""
    var GrandTotal = ""

    var buynow=""
    var variationid:Int=0
    var productid=0
    var qty=0
    var unit = ""
    var mrp = 0.0f
    var price = 0.0f
    var discount =0
    var variationQty = ""
    var varproductpricecal = 0.0f

    var name = ""
    var mobilenumber = ""
    var pincode = ""
    var town = ""
    var state = ""
    var flat = ""
    var area = ""
    var landmark = ""
    var userid = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddtoAddressBinding.inflate(layoutInflater, container, false)

        userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()

         subtotal = UserObject.finalAmount//arguments?.getFloat("subtotal")!!.toFloat()
        DeliveryCharge = arguments?.getString("DeliveryCharge").toString()
         GrandTotal = UserObject.finalAmount.toString()//arguments?.getString("total").toString()


        var buynow = arguments?.getString("buynow")

        if (buynow.equals("buynow"))
        {
              variationid = arguments?.getInt("Variationid")!!
              productid = arguments?.getInt("productid")!!
              qty = arguments?.getInt("qty")!!
              unit = arguments?.getString("unit")!!
              mrp = arguments?.getFloat("mrp")!!
              price = arguments?.getFloat("price")!!
              discount = arguments?.getInt("discount")!!
              variationQty = arguments?.getString("variationqty")!!
              varproductpricecal = arguments?.getFloat("varproductpricecal")!!

        }


        var advanceBooking = arguments?.getString("advanceBooking")

        if (buynow.equals("advanceBooking"))
        {
            variationid = arguments?.getInt("Variationid")!!
            productid = arguments?.getInt("productid")!!
            qty = arguments?.getInt("qty")!!
            unit = arguments?.getString("unit")!!
            mrp = arguments?.getFloat("mrp")!!
            price = arguments?.getFloat("price")!!
            discount = arguments?.getInt("discount")!!
            variationQty = arguments?.getString("variationqty")!!
            varproductpricecal = arguments?.getFloat("varproductpricecal")!!

        }

        binding.llConfirm.setOnClickListener(this)
        binding.imgBack.setOnClickListener(this)
        return binding.root
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {


            R.id.ll_confirm -> {
                name = binding.edFullName.text.toString()
                mobilenumber = binding.edMobileNumber.text.toString()
                pincode = binding.edPincode.text.toString()
                town = binding.edTown.text.toString()
                state = binding.edState.text.toString()
                flat = binding.edFlat.text.toString()
                area = binding.edArea.text.toString()
                landmark = binding.edLandmark.text.toString()

                addAddress(
                    name, mobilenumber, pincode, town, state,
                    flat, area, landmark
                )

            }
            R.id.img_back -> {
                requireActivity().popFragment()
            }

        }


    }

    private fun addAddress(
        name: String,
        mobilenumber: String,
        pincode: String,
        town: String,
        state: String,
        flat: String,
        area: String,
        landmark: String
    ) {
        if (TextUtils.isEmpty(name)) {
            binding.edFullName.setError("Enter your name ")
            binding.edFullName.requestFocus()

        } else if (TextUtils.isEmpty(mobilenumber)) {
            binding.edMobileNumber.setError("Enter your Phone No")
            binding.edMobileNumber.requestFocus()
        } else if (!isPhoneValid(mobilenumber)) {
            binding.edMobileNumber.setError("Enter your valid phone number")
            binding.edMobileNumber.requestFocus()
        } else if (TextUtils.isEmpty(pincode)) {
            binding.edPincode.setError("Enter your pincode")
            binding.edPincode.requestFocus()

        } else if (!isPinValid(pincode)) {
            binding.edPincode.setError("Enter your valid pincode")
            binding.edPincode.requestFocus()
        } else if (TextUtils.isEmpty(town)) {
            binding.edPincode.setError("Enter town or city")
            binding.edPincode.requestFocus()


        } else if (TextUtils.isEmpty(state)) {
            binding.edPincode.setError("Enter state")
            binding.edPincode.requestFocus()


        } else if (TextUtils.isEmpty(flat)) {
            binding.edPincode.setError("Enter flat,house no, building, company name")
            binding.edPincode.requestFocus()

        } else if (TextUtils.isEmpty(area)) {
            binding.edPincode.setError("Enter area,colony,street,sector,village")
            binding.edPincode.requestFocus()

        } else if (TextUtils.isEmpty(landmark)) {
            binding.edPincode.setError("Enter landmark")
            binding.edPincode.requestFocus()

        } else if (InternetAccess.isConnected(requireContext())) {
            val call = RetrofitClient.apiservice.addUserAddress(
                userid, name, mobilenumber, pincode, state, flat, area, landmark
            )
            call.enqueue(object : Callback<AddToCartResponse> {
                override fun onResponse(
                    call: Call<AddToCartResponse>,
                    response: Response<AddToCartResponse>
                ) {

                    if (response.isSuccessful) {
                        if (response.body()?.status == "true") {

                            var buynow = arguments?.getString("buynow")
                            var advanceBooking = arguments?.getString("advanceBooking")

                            var subtotal = arguments?.getFloat("subtotal")
                            var DeliveryCharge = arguments?.getString("DeliveryCharge")
                            var GrandTotal = arguments?.getString("total")


                            if (buynow.equals("buynow"))
                            {
                                val userbundle = bundleOf(
                                    "Variationid"  to   variationid,
                                    "productid" to  productid,
                                    "qty" to  qty,
                                    "unit" to  unit,
                                    "mrp"  to  mrp,
                                    "price"  to  price,
                                    "discount"  to  discount,
                                    "variationqty" to variationQty,
                                    "varproductpricecal" to varproductpricecal,
                                    "buynow" to "buynow",
                                    "subtotal" to subtotal,
                                    "DeliveryCharge" to DeliveryCharge,
                                    "total" to GrandTotal
                                )
                                requireActivity().nextFragment(
                                    R.id.action_addtoAddressFragment_to_checkoutFragment,
                                    userbundle
                                )

                            }
                            else if (advanceBooking.equals("advanceBooking"))
                            {
                                val userbundle = bundleOf(
                                    "Variationid"  to   variationid,
                                    "productid" to  productid,
                                    "qty" to  qty,
                                    "unit" to  unit,
                                    "mrp"  to  mrp,
                                    "price"  to  price,
                                    "discount"  to  discount,
                                    "variationqty" to variationQty,
                                    "varproductpricecal" to varproductpricecal,
                                    "buynow" to "buynow",
                                    "subtotal" to subtotal,
                                    "DeliveryCharge" to DeliveryCharge,
                                    "total" to GrandTotal
                                )
                                requireActivity().nextFragment(
                                    R.id.action_addtoAddressFragment_to_checkoutFragment,
                                    userbundle
                                )

                            }

                            else {
                                val userbundle = bundleOf(
                                    "subtotal" to subtotal,
                                    "DeliveryCharge" to DeliveryCharge,
                                    "total" to GrandTotal
                                )
                                requireActivity().nextFragment(
                                    R.id.action_addtoAddressFragment_to_checkoutFragment,
                                    userbundle
                                )

                            }

                            binding.edFullName.setText("")
                            binding.edMobileNumber.setText("")
                            binding.edPincode.setText("")
                            binding.edTown.setText("")
                            binding.edState.setText("")
                            binding.edFlat.setText("")
                            binding.edArea.setText("")
                            binding.edLandmark.setText("")

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "" + response.body()?.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "" + t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

            })
        } else {
            Toast.makeText(requireContext(), "not internet connected", Toast.LENGTH_SHORT).show()
        }


    }


    private fun isPhoneValid(phone: String): Boolean {
        return phone.length > 9
    }

    private fun isPinValid(pincode: String): Boolean {
        return pincode.length > 5
    }
}