package edu.gatech.seclass.ecommerence_app.viewmodel
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.gatech.seclass.ecommerence_app.data.User
import edu.gatech.seclass.ecommerence_app.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
// LoginRegisterViewModel extends ViewModel
// FirebaseAuth instance is injected
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): ViewModel() {

    // _register: contains state of registration action (mutable)
    // register: immutable view of _register
    private val _register = MutableStateFlow<Resource<FirebaseUser>>(Resource.Unspecified())
    val register: Flow<Resource<FirebaseUser>> = _register

    // function attempting to create a new user account with firebaseAuth's func createUserWithEmailAndPassword
    fun createAccountWithEmailAndPassword(user: User, password: String){
        runBlocking {
            _register.emit(Resource.Loading())
        }
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
            .addOnSuccessListener {
                it.user?.let {
                    _register.value = Resource.Success(it)
                }
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }
}