package raj.helpservice.android.helpservice.fragment.consumer

import android.arch.lifecycle.ViewModel;
import raj.helpservice.android.helpservice.Repository
import raj.helpservice.android.helpservice.customviews.CreateService

class CreateRequestViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val repository = Repository()


    fun createRequest(createService: CreateService) = repository.createService(createService = createService)



}
