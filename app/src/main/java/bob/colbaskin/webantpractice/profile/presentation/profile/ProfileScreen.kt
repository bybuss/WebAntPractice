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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import bob.colbaskin.webantpractice.common.design_system.theme.CustomTheme
import bob.colbaskin.webantpractice.common.utils.toFormattedDate
import bob.colbaskin.webantpractice.profile.domain.models.User

@Composable
fun ProfileScreenRoot(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    ProfileScreen(
        state = viewModel.state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ProfileScreen(state: ProfileState, onAction: (ProfileAction) -> Unit) {
    when (val userState = state.user) {
        is UiState.Success -> {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                ProfileHeader(state = state, user = userState)
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
fun ProfileHeader(
    state: ProfileState, // FIXME: MOST LIKELY THE IMAGE PARAM WILL BE IN THE USER
    user: UiState.Success<User>
) {
    Spacer(modifier = Modifier.height(20.dp))
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(CustomTheme.colors.white)
                .border(1.dp, CustomTheme.colors.grayLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            when (val imageState = state.image) {
                is UiState.Success -> Image(
                    bitmap = imageState.data,
                    contentDescription = stringResource(R.string.profile_photo),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                is UiState.Loading -> LoadingIndicator(
                    modifier = Modifier.fillMaxSize(),
                    isIndicatorOnly = true
                )

                is UiState.Error -> Icon(
                    painter = painterResource(R.drawable.webant_error_logo),
                    contentDescription = null,
                    tint = CustomTheme.colors.graySecondary,
                    modifier = Modifier.wrapContentSize()
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = user.data.displayName,
                style = CustomTheme.typography.h3,
                color = CustomTheme.colors.black
            )
            Text(
                text = user.data.birthday.toFormattedDate(),
                style = CustomTheme.typography.p,
                color = CustomTheme.colors.gray
            )
        }
    }
    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .alpha(0.5f),
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
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

