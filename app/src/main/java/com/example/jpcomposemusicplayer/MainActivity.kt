package com.example.jpcomposemusicplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.jpcomposemusicplayer.ui.theme.JPComposeMusicPlayerTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPComposeMusicPlayerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var isPlaying by remember { mutableStateOf(true) }
                    val context = LocalContext.current
                    val mVideoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
                    val exoplayer =  ExoPlayer.Builder(context).build().apply {
                        setMediaItem(MediaItem.fromUri(Uri.parse(mVideoUrl)))
                        prepare()

                    }
                    MusicPlayer(
                        modifier = Modifier,
                        isPlaying = {isPlaying},
                        onReplayClick = {exoplayer.seekBack()},
                        onPauseToggle = {exoplayer.seekForward()},
                        onForwardClick = {
                            if (exoplayer.isPlaying){
                                exoplayer.play()
                            }
                            isPlaying = isPlaying.not()
                        }
                    )
                }
            }
        }
    }
}


@Composable
fun MusicPlayer(
    modifier: Modifier = Modifier,
    isPlaying: () -> Boolean,
    onReplayClick: () -> Unit,
    onPauseToggle: () -> Unit,
    onForwardClick: () -> Unit
) {
    val context = LocalContext.current
    val isVideoPlaying = remember(isPlaying()) { isPlaying() }

    val mVideoUrl = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4"
    val mExoplayer =  ExoPlayer.Builder(context).build().apply {
        setMediaItem(MediaItem.fromUri(Uri.parse(mVideoUrl)))
        prepare()

    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Black)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)) {

            mExoplayer.playWhenReady=true
            AndroidView(modifier = Modifier.height(200.dp).fillMaxWidth(), factory = {context ->
                PlayerView(context).apply {
                    player = mExoplayer
                }
            })
        }

//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            IconButton(onClick = { onReplayClick }, modifier = Modifier.size(40.dp)) {
//                Image(modifier = Modifier.fillMaxSize(),contentScale = ContentScale.Crop,
//                    painter = painterResource(id = R.drawable.ic_baseline_replay_5_24),
//                    contentDescription = "replay 5sec"
//                )
//            }
//            IconButton(modifier = Modifier.size(40.dp),onClick = { onPauseToggle }) {
//                Image(modifier = Modifier.fillMaxSize(),contentScale = ContentScale.Crop,
//                    painter = if (isVideoPlaying){
//                        painterResource(id = R.drawable.ic_baseline_pause_24)
//                    } else{
//                        painterResource(id = R.drawable.ic_baseline_play_arrow_24)
//                    },
//                    contentDescription = "play/pause"
//                )
//            }
//
//           IconButton(modifier = Modifier.size(40.dp),onClick = onForwardClick) {
//               Image(modifier = Modifier.fillMaxSize(),contentScale = ContentScale.Crop,
//                   painter = painterResource(id = R.drawable.ic_baseline_forward_10_24),
//                   contentDescription = "forward 10sec"
//               )
//           }
//
//        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JPComposeMusicPlayerTheme {
        MusicPlayer(
            modifier = Modifier.fillMaxSize(),
            isPlaying = {true},
            onReplayClick = {},
            onPauseToggle = {},
            onForwardClick = {}
        )
    }
}