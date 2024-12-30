package com.codek.deliverypds.ui.registers.category.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.codek.deliverypds.app.configs.BarNavigation
import com.codek.deliverypds.app.theme.ColorError
import com.codek.deliverypds.app.theme.ColorSec
import com.codek.deliverypds.app.theme.DarkColorError
import com.codek.deliverypds.ui.registers.category.state.CategoryUiState
import com.codek.deliverypds.ui.registers.category.viewmodel.CategoryViewModel
import com.codek.deliverypds.ui.registers.components.RegistersHeader
import com.codek.deliverypds.ui.registers.product.state.ProductUiState
import com.codek.deliverypds.ui.registers.product.viewmodel.ProductViewModel

@Composable
fun CategoryScreenList(
    categoryViewModel: CategoryViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToConfig: () -> Unit,
    onSignOutClick: () -> Unit
) {

    LaunchedEffect(Unit) {
        categoryViewModel.loadCategories()
    }

    BarNavigation(
        onNavigateToHome = { onNavigateToHome() },
        onNavigateToConfig = { onNavigateToConfig() },
        onSignOut = { onSignOutClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray.copy(alpha = 0.5f)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RegistersHeader("Categorias Cadastradas")
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (!categoryViewModel.categories.value.isNullOrEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categoryViewModel.categories.value!!) { category ->
                            CategoriesItemRow(
                                category = category
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Nenhuma categoria cadastrada",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesItemRow(
    category: CategoryUiState
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                RoundedCornerShape(16.dp)
            )
            .clip(RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(category.link),
                contentDescription = category.category,
                modifier = Modifier
                    .size(60.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(100)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = category.category,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryScreenList() {
    val categoryUiState = CategoryUiState(
        category = "Cozinha",
        link = "" // Link de imagem simulada
    )
    CategoriesItemRow(
        category = categoryUiState
    )
}