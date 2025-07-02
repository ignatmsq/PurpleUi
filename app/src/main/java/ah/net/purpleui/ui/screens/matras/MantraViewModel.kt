package ah.net.purpleui.ui.screens.matras

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ah.net.purpleui.ui.screens.matras.entities.Mantra
import ah.net.purpleui.ui.screens.matras.data.AssetsMantrasProviderImpl
import ah.net.purpleui.ui.screens.matras.data.MantrasProvider

class MantraViewModel(
    private val mantrasProvider: MantrasProvider = AssetsMantrasProviderImpl()
) : ViewModel() {

    var state by mutableStateOf(MantraState())
        private set

    fun loadMantraList(context: Context, fileName: String, title: String) {
        val mantras = mantrasProvider.loadMantras(context, fileName)
        state = state.copy(
            mantras = mantras,
            title = title,
            showBottomSheet = true
        )
    }

    fun hideBottomSheet() {
        state = state.copy(showBottomSheet = false)
    }

    fun shuffleMantras() {
        state = state.copy(mantras = simpleShuffleList(state.mantras))
    }

    private fun <T> simpleShuffleList(list: List<T>): List<T> {
        return list.shuffled()
    }
}

data class MantraState(
    val mantras: List<Mantra> = emptyList(),
    val title: String = "",
    val showBottomSheet: Boolean = false
) 