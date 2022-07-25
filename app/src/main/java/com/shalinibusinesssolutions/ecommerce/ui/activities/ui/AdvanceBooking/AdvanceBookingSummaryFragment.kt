
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.shalinibusinesssolutions.ecommerce.R
import com.shalinibusinesssolutions.ecommerce.databinding.AdvancebookingSinglerawBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentAddtoAddressBinding
import com.shalinibusinesssolutions.ecommerce.databinding.FragmentAdvanceBookingSummaryBinding
import com.shalinibusinesssolutions.ecommerce.ui.adapter.AdvanceBookingAdapter
import com.shalinibusinesssolutions.ecommerce.ui.api.RetrofitClient
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.WishListDetailList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.advanceOrderList
import com.shalinibusinesssolutions.ecommerce.ui.apimodel.getadvancepaymentorderResponse
import com.shalinibusinesssolutions.ecommerce.ui.utility.Fragmentinterface.AdvanceBookingInterface
import com.shalinibusinesssolutions.ecommerce.ui.utility.SharedPreferencesUtil
import com.shalinibusinesssolutions.ecommerce.ui.utility.UserObject
import com.shalinibusinesssolutions.ecommerce.ui.utility.nextFragment
import com.shalinibusinesssolutions.ecommerce.ui.utility.popFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdvanceBookingSummaryFragment : Fragment(),View.OnClickListener,AdvanceBookingInterface {

    lateinit var binding : FragmentAdvanceBookingSummaryBinding
    lateinit var advanceBookingAdapter: AdvanceBookingAdapter
    var listadvancebook=ArrayList<advanceOrderList>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding= FragmentAdvanceBookingSummaryBinding.inflate(layoutInflater,container,false)
        binding.recAdvance.layoutManager=LinearLayoutManager(requireContext())
        advanceBookingAdapter=AdvanceBookingAdapter(this)
        binding.progressbar.visibility=View.VISIBLE
        binding.imgBack.setOnClickListener(this)
        getAdvanceBookingData()


        return binding.root

    }

    private fun getAdvanceBookingData() {

        var  userid = SharedPreferencesUtil().getUserId(requireContext()).toString().toInt()
        val call=RetrofitClient.apiservice.getAdvancePaymentRecord(userid)
        call.enqueue(object : Callback<getadvancepaymentorderResponse>{
            override fun onResponse(
                call: Call<getadvancepaymentorderResponse>,
                response: Response<getadvancepaymentorderResponse>
            ) {

                 if (response.isSuccessful)
                 {

                     if (response.body()?.status=="true")
                     {
                         listadvancebook=response.body()?.data!!
                         advanceBookingAdapter.Populistitem(listadvancebook)
                         binding.recAdvance?.adapter=advanceBookingAdapter
                         binding.progressbar?.visibility=View.GONE

                     }
                 }
            }

            override fun onFailure(call: Call<getadvancepaymentorderResponse>, t: Throwable) {

            }

        })

    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.img_back->
            {
                requireActivity().popFragment()
            }
        }
    }

    override fun advanceBookingInterface(
        advanceOrderList: advanceOrderList,
        status: String,
        binding: AdvancebookingSinglerawBinding,
        position: Int
    ) {
        UserObject.orderid=advanceOrderList.orderid
        UserObject.transactionno=advanceOrderList.transactionno
        UserObject.Ordervalue=advanceOrderList.Ordervalue
        UserObject.advancepayment=advanceOrderList.advancepayment
        UserObject.address_id=advanceOrderList.addressid
        UserObject.productid=advanceOrderList.productid
        UserObject.variationid=advanceOrderList.variationid
        UserObject.mrp=advanceOrderList.mrp
        UserObject.price=advanceOrderList.price
        UserObject.varproductpricecal=advanceOrderList.pricecal
        UserObject.qty=advanceOrderList.qty.toInt()
        UserObject.variationQty=advanceOrderList.varqty
        UserObject.unit=advanceOrderList.unit
        UserObject.discount=advanceOrderList.discount.toInt()
        UserObject.grandtotalamount=advanceOrderList.Ordervalue
        UserObject.finalAmount=advanceOrderList.Ordervalue-UserObject.advancepayment
        UserObject.advanceBookingFragment="yes"

        requireActivity().nextFragment(R.id.action_advanceBookingSummaryFragment_to_checkoutFragment)
    }



}
