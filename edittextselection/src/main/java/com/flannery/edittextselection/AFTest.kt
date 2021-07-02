import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.savedstate.SavedStateRegistry
import com.flannery.edittextselection.R

class MyActivity : AppCompatActivity(R.layout.activity_main) {


    companion object {
        private const val MY_SAVED_STATE_KEY = "my_saved_state"
        private const val SOME_VALUE_KEY = "some_value"
    }

    fun test2() {

    }


    private lateinit var someValue: String

    private val savedStateProvider = SavedStateRegistry.SavedStateProvider {
        Bundle().apply {
            putString(SOME_VALUE_KEY, someValue)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        savedStateRegistry.registerSavedStateProvider(MY_SAVED_STATE_KEY, savedStateProvider)
    }

    fun someMethod() {
        someValue = savedStateRegistry.consumeRestoredStateForKey(MY_SAVED_STATE_KEY)?.getString(
            SOME_VALUE_KEY
        ) ?: ""
    }
}


class MyFragmentActivity : FragmentActivity(R.layout.activity_main)


class MyFragment : Fragment(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }


    fun test() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Do something
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        val obj = FragmentManager.OnBackStackChangedListener { }
//        requireActivity().supportFragmentManager.addOnBackStackChangedListener()
    }

    fun test2() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }
}