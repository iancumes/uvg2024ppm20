package edu.uvg.codelabapp1

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue //Add this, if not by rememember wont work
import androidx.compose.runtime.setValue //Add this, if not by rememember wont work
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.coerceAtLeast
import edu.uvg.codelabapp1.ui.theme.CodelabApp1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CodelabApp1Theme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit
    , modifier: Modifier = Modifier
){

    Column (
        modifier = modifier.fillMaxSize()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview(){
    CodelabApp1Theme{
        OnboardingScreen( onContinueClicked = {})
    }
}

@Composable
fun MyApp(
        modifier: Modifier = Modifier
    ){

    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface (modifier) {
        if (shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false})
        } else {
            Greetings()
        }
    }

}

@Preview
@Composable
fun MyAppPreview(){
    CodelabApp1Theme {
        MyApp(Modifier.fillMaxSize())
    }
}

@Composable
private fun Greetings(
    modifier: Modifier = Modifier
    , names: List<String> = List(1000){ "$it" }
){
    LazyColumn (modifier = modifier.padding(vertical = 4.dp)){
        items(items = names){
            name -> Greeting(name = name)
        }
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun GreetingsPreview(){
    CodelabApp1Theme {
        Greetings()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    //State variable
    var expanded by rememberSaveable { mutableStateOf(false) }

    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
        , animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy
            , stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(vertical = 4.dp, horizontal = 8.dp),
    ) {
        Row (modifier = Modifier.padding(24.dp)){
            Column (modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello ")
                Text(
                    text = name
                    , style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(text = if (expanded) "Show less" else "Show more")
            }
        }
    }

}

@Preview(
    showBackground = true
    , widthDp = 320
    , uiMode = Configuration.UI_MODE_NIGHT_YES
    , name = "GreetingPreviewDark"
)
@Composable
fun GreetingPreview() {
    CodelabApp1Theme {
        MyApp()
    }
}