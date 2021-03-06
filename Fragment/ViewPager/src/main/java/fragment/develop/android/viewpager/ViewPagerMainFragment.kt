package fragment.develop.android.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_view_pager.*

class ViewPagerMainFragment : Fragment() {

    companion object {
        fun startFragment(position: Int): ViewPagerMainFragment {
            val fragment = ViewPagerMainFragment()
            val bundle = Bundle()
            bundle.putInt("index", position)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var index: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val arguments = arguments
        if (null != arguments) {
            index = arguments.getInt("index")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tv.text = index.toString()
    }
}
