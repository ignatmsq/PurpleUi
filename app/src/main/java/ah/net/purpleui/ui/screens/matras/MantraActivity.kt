package ah.net.purpleui.ui.screens.matras

import ah.net.purpleui.R
import ah.net.purpleui.ui.screens.matras.entities.Mantra
import ah.net.purpleui.ui.screens.matras.widgets.MantraAnimationSpecs
import ah.net.purpleui.ui.screens.matras.widgets.MantraItem
import ah.net.purpleui.ui.widgets.CustomButton
import ah.net.purpleui.ui.widgets.ShuffleButton
import ah.net.purpleui.ui.theme.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import androidx.core.view.WindowInsetsControllerCompat

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Configure window to be edge-to-edge
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            PurpleUiTheme(darkTheme = true) {
                val systemUiController = rememberSystemUiController()
                
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = Color(0xFF100D17),
                        darkIcons = false
                    )
                }
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ScreenBackground
                ) {
                    MantraScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MantraScreen(viewModel: MantraViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val context = LocalContext.current
    val systemBarsPaddings = WindowInsets.systemBars.asPaddingValues()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ScreenBackground
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(WindowInsets.systemBars),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val listTitle = stringResource(R.string.mantras_list_title)
                    CustomButton(
                        text = stringResource(R.string.open_mantras_list),
                        onClick = { viewModel.loadMantraList(
                            context, 
                            "short_mantras.json",
                            listTitle
                        ) }
                    )
                    
                    val longListTitle = stringResource(R.string.long_mantras_list_title)
                    CustomButton(
                        text = stringResource(R.string.open_long_mantras_list),
                        onClick = { viewModel.loadMantraList(
                            context, 
                            "mantras.json",
                            longListTitle
                        ) }
                    )
                    
                    val emptyListTitle = stringResource(R.string.empty_mantras_list_title)
                    CustomButton(
                        text = stringResource(R.string.open_empty_mantras_list),
                        onClick = { viewModel.loadMantraList(
                            context, 
                            "empty_mantras.json",
                            emptyListTitle
                        ) }
                    )
                }
            }
        }

        if (viewModel.state.showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { viewModel.hideBottomSheet() },
                sheetState = bottomSheetState,
                containerColor = BottomSheetBackground,
                contentColor = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxSize(),
                dragHandle = {
                    Box(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 8.dp)
                            .size(width = 48.dp, height = 4.dp)
                            .background(
                                DragHandle,
                                MaterialTheme.shapes.extraLarge
                            )
                    )
                },
                windowInsets = WindowInsets(
                    top = systemBarsPaddings.calculateTopPadding() + 16.dp,
                ),
                scrimColor = Color.Transparent,
            ) {
                MantraListContent(
                    mantras = viewModel.state.mantras,
                    title = viewModel.state.title,
                    onShuffleClick = { 
                        viewModel.shuffleMantras()
                    }
                )
            }
        }
    }
}

@Composable
fun MantraListContent(
    mantras: List<Mantra>,
    title: String,
    onShuffleClick: () -> Unit
) {
    val listState = rememberLazyListState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = title,
            style = ListTitleStyle,
            color = MantraText,
            modifier = Modifier.padding(bottom = 16.dp, top = 16.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Box(
            modifier = Modifier.weight(1f)
        ) {
            AnimatedContent(
                targetState = mantras,
                transitionSpec = {
                    MantraAnimationSpecs.enterTransition togetherWith MantraAnimationSpecs.exitTransition
                },
                label = "MantraListAnimation"
            ) { animatedMantras ->
                if (animatedMantras.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.empty_list_text),
                            style = MaterialTheme.typography.bodyLarge,
                            color = EmptyListText,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        state = listState,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = animatedMantras
                        ) { mantra ->
                            MantraItem(
                                mantra = mantra,
                                onPlayClick = { /* TODO: Implement play */ }
                            )
                        }
                    }
                }
            }
        }

        if (mantras.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))
            ShuffleButton(
                text = stringResource(R.string.shuffle),
                onClick = onShuffleClick
            )
            Spacer(modifier = Modifier.height(
                WindowInsets.systemBars.asPaddingValues().calculateBottomPadding() + 12.dp
            ))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MantraScreenPreview() {
    PurpleUiTheme(darkTheme = true) {
        MantraScreen()
    }
}