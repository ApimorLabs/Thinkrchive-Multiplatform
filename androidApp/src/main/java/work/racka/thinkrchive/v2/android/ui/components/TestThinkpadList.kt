package work.racka.thinkrchive.v2.android.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.getViewModel
import states.list.ThinkpadListSideEffect
import timber.log.Timber
import work.racka.thinkrchive.v2.common.integration.viewmodels.ThinkpadListViewModel

@ExperimentalAnimationApi
@Composable
fun TestThinkpadList(viewModel: ThinkpadListViewModel = getViewModel()) {

    val thinkpadListState by viewModel.host.state.collectAsState()
    val sideEffect = viewModel.host.sideEffect
        .collectAsState(initial = ThinkpadListSideEffect.Network())
        .value as ThinkpadListSideEffect.Network

    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(thinkpadListState.thinkpadList) {
            Timber.d("items called")
            Text(text = it.model, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(text = "Release Data: ${it.releaseDate}")
            Text(text = "Processors: ${it.processors}")
            Text(text = "Platform: ${it.processorPlatforms}")
            Text(text = sideEffect.errorMsg)
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            AnimatedVisibility(sideEffect.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}