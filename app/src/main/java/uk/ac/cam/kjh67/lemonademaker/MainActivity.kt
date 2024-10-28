package uk.ac.cam.kjh67.lemonademaker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.ac.cam.kjh67.lemonademaker.ui.theme.LemonadeMakerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeMakerTheme {
                LemonadeMakerApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun LemonadeMakerApp() {
    CenterAlignedTopAppBar(
        title = { Text(
            text = "Lemonade Maker",
            fontWeight = FontWeight.Bold,
            color = Color.Black) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Yellow
        )
    )
    LemonadeMakerContent(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.Center)
    )
}

@Composable
fun LemonadeMakerContent(modifier: Modifier = Modifier) {
    var lemonadePhase by remember { mutableStateOf(0) }
    var lemonClicksNeeded by remember { mutableStateOf(0) }
    val lemonadeText = when (lemonadePhase) {
        0 -> R.string.stageOneText
        1 -> R.string.stageTwoText
        2 -> R.string.stageThreeText
        else -> R.string.stageFourText
    }
    val lemonadeImage = when (lemonadePhase) {
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }
    val lemonadeImageDescriptor = when (lemonadePhase) {
        0 -> R.string.stageOneImageDescriptor
        1 -> R.string.stageTwoImageDescriptor
        2 -> R.string.stageThreeImageDescriptor
        else -> R.string.stageFourImageDescriptor
    }

    val clickLambda = when (lemonadePhase) {
        0 -> { ->
            lemonadePhase += 1
            lemonClicksNeeded = (2..4).random()
        }
        1 -> { ->
            if (lemonClicksNeeded > 1) { lemonClicksNeeded -= 1 }
            else { lemonadePhase += 1 } }
        2 -> { -> lemonadePhase += 1 }
        else -> { -> lemonadePhase = 0 }
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = lemonadeImage),
            contentDescription = stringResource(id = lemonadeImageDescriptor),
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(25))
                .background(Color.Cyan)
                .clickable(onClick = clickLambda)
        )
        Spacer(Modifier.height(16.dp))
        Text(text = stringResource(id = lemonadeText))
    }
}