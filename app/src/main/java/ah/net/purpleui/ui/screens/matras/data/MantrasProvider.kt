package ah.net.purpleui.ui.screens.matras.data

import ah.net.purpleui.ui.screens.matras.entities.Mantra
import android.content.Context

interface MantrasProvider {
    suspend fun loadMantras(context: Context, fileName: String): List<Mantra>
} 