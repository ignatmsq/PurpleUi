package ah.net.purpleui.ui.screens.matras.data

import ah.net.purpleui.ui.screens.matras.entities.Mantra
import android.content.Context
import org.json.JSONObject
import java.io.IOException

class AssetsMantrasProviderImpl : MantrasProvider {
    override fun loadMantras(context: Context, fileName: String): List<Mantra> {
        return try {
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val jsonObject = JSONObject(jsonString)
            val mantrasArray = jsonObject.getJSONArray("mantras")

            val mantras = mutableListOf<Mantra>()
            for (i in 0 until mantrasArray.length()) {
                val mantraObject = mantrasArray.getJSONObject(i)
                mantras.add(
                    Mantra(
                        name = mantraObject.getString("name"),
                        duration = mantraObject.getString("duration")
                    )
                )
            }
            mantras
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
} 