package bob.colbaskin.webantpractice.profile.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import bob.colbaskin.webantpractice.R
import bob.colbaskin.webantpractice.common.UiState
import bob.colbaskin.webantpractice.common.design_system.ErrorIndicator
import bob.colbaskin.webantpractice.common.design_system.LoadingIndicator
import bob.colbaskin.webantpractice.common.design_system.SettingsTopAppBar
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.user_prefs.domain.models.User
import bob.colbaskin.webantpractice.common.utils.toFormattedDate
import bob.colbaskin.webantpractice.navigation.Screens
import bob.colbaskin.webantpractice.profile.presentation.common.ProfileState
import coil.compose.rememberAsyncImagePainter

@Composable
fun ProfileScreenRoot(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            SettingsTopAppBar(
                onSettingsClick = { navController.navigate(Screens.Settings) }
            )
        },
        contentWindowInsets = WindowInsets(0),
        contentColor = CustomTheme.colors.black,
        containerColor = CustomTheme.colors.white
    ) { innerPadding ->
        ProfileScreen(
            state = viewModel.state,
            onAction = viewModel::onAction,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (val userState = state.user) {
        is UiState.Success -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            ) {
                ProfileHeader(user = userState.data)
                Spacer(modifier = Modifier.height(16.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    repeat(100) {
                        item {
                            PhotoItem(onAction)
                        }
                    }
                }
            }
        }
        is UiState.Error -> {
            ErrorIndicator(
                title = userState.title,
                text = userState.text,
                modifier = Modifier.fillMaxSize()
            )
        }
        UiState.Loading -> LoadingIndicator(modifier = Modifier.fillMaxSize())
    }
}

@Composable
fun ProfileHeader(user: User) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(CustomTheme.colors.white)
                .border(1.dp, CustomTheme.colors.grayLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (user.userProfilePhoto != null) {
                Image(
                    painter = rememberAsyncImagePainter(user.userProfilePhoto), // FIXME: REPLACE PAINTER TO IMAGE BITMAP FROM API
                    contentDescription = stringResource(R.string.profile_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape).fillMaxSize()
                )
            } else {
                Image(
                    painter = painterResource(R.drawable.webant_logo),
                    contentDescription = stringResource(R.string.profile_photo),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 9.dp, horizontal = 10.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = user.displayName,
                style = CustomTheme.typography.h3,
                color = CustomTheme.colors.black
            )
            Text(
                text = user.birthday.toFormattedDate(),
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.gray
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.3f),
        color = CustomTheme.colors.main,
        thickness = 1.dp
    )
}

@Composable
private fun PhotoItem(onAction: (ProfileAction) -> Unit) {
    Box(
        modifier = Modifier
            .size(78.dp)
            .clip(CustomTheme.shapes.photoSmall)
            .background(CustomTheme.colors.grayLight)
            .clickable(onClick = {

            })
    ) {
        Image(
            painter = painterResource(R.drawable.webant_logo),
            contentDescription = stringResource(R.string.fetched_photo_description),
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}
