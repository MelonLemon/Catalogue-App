package com.melonlemon.catalogueapp.feature_catalogue.presentation.core_components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.melonlemon.catalogueapp.R
import com.melonlemon.catalogueapp.feature_catalogue.domain.model.CategoryInfo
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.AddEditFileEvents
import com.melonlemon.catalogueapp.feature_catalogue.presentation.add_edit_file.util.ColumnType
import com.melonlemon.catalogueapp.feature_catalogue.presentation.home.NewFolderEvents
import com.melonlemon.catalogueapp.ui.theme.CatalogueAppTheme

@Composable
fun BackArrowRow(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit,
    title: String
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        IconButton(
            onClick = onArrowBackClick,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Arrow Back",
                tint = MaterialTheme.colorScheme.onBackground)
        }


        Text(
            modifier = modifier.fillMaxWidth(),
            text = title,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun CheckboxText(
    modifier: Modifier = Modifier,
    title: String,
    checkedState: Boolean,
    onStateChange: (Boolean) -> Unit = { }
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .toggleable(
                value = checkedState,
                onValueChange = { onStateChange(!checkedState) },
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checkedState,
            onCheckedChange = null // null recommended for accessibility with screenreaders
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}



fun LazyListScope.addCards(
    text: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    onAddBtnClick: () -> Unit,
    listOfCards: List<String>
){
    item{
        TextInputAdd(
            text = text,
            onTextChanged = onTextChanged,
            placeholder = placeholder,
            onAddBtnClick = onAddBtnClick
        )
    }
    items(listOfCards){ card ->
        LineTextCard(
            modifier = Modifier.fillMaxWidth(),
            text = card
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

fun LazyListScope.addCheckboxText(
    text: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    onAddBtnClick: () -> Unit,
    listOfCheckboxText: List<String>,
    listOfSelected: List<Int>,
    onCheckStateChange: (Int, Boolean) -> Unit
){
    item{
        TextInputAdd(
            text = text,
            onTextChanged = { name ->
                onTextChanged(name)
            },
            placeholder = placeholder,
            onAddBtnClick = onAddBtnClick
        )
    }
    itemsIndexed(listOfCheckboxText){ index, checkboxText ->
        CheckboxText(
            modifier = Modifier.fillMaxWidth(),
            title = checkboxText,
            checkedState = index in listOfSelected,
            onStateChange = {  checkStatus ->
                onCheckStateChange(
                    index, checkStatus
                )
            }
        )
        Spacer(modifier = Modifier.width(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextInputAdd(
    text: String,
    placeholder: String,
    onTextChanged: (String) -> Unit,
    onAddBtnClick: () -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            value = text,
            onValueChange = onTextChanged,
            shape = MaterialTheme.shapes.extraSmall,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            ),
            placeholder = { Text(
                text= placeholder
            ) }
        )
        Spacer(modifier = Modifier.width(8.dp))
        AddButton(
            modifier = Modifier.size(56.dp),
            onButtonClicked = onAddBtnClick
        )
    }
}

@Composable
fun ColumnsDialog(
    onSaveClick: (Int) -> Unit,
    onCancelClick: () -> Unit,
    columnType: ColumnType,
    listColumn: List<String>,
    selectedIndex: Int
) {

    var temptIndex by remember { mutableStateOf(selectedIndex) }
    Dialog(
        onDismissRequest = { onCancelClick()},
    ) {
        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(11.dp)
            ),
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.Start
            ){
                item{
                    Text(
                        text = stringResource(R.string.choose) + " " + columnType.name + " " +
                                stringResource(R.string.column),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
                itemsIndexed(listColumn){index, name ->
                    SelectLineTextCard(
                        modifier = Modifier.fillMaxWidth(),
                        text = name,
                        isSelected = index==temptIndex,
                        onCardClick = {
                            temptIndex = index
                        }
                    )
                    Divider(modifier = Modifier.height(1.dp),
                        color = MaterialTheme.colorScheme.outlineVariant)
                }

                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ){
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = { onCancelClick() }
                        ) {
                            Text(
                                text = stringResource(R.string.cancel),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            onClick = { onSaveClick(temptIndex) }
                        ) {
                            Text(
                                text = stringResource(R.string.save),
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

            }
        }

    }
    
}

@Composable
fun ErrorMsgView(
    modifier: Modifier = Modifier,
    text: String,
    imageBitmap: ImageBitmap
) {
    Box(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ){
            Text(text= text)
            Image(bitmap = imageBitmap, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BackArrowRowPreview() {
    CatalogueAppTheme{
        BackArrowRow(
            onArrowBackClick = {

            },
            title = "Folders"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ColumnsDialogPreview() {
    CatalogueAppTheme{
        ColumnsDialog(
            onSaveClick = {

            },
            onCancelClick = {

            },
            columnType = ColumnType.TitleColumn("Example"),
            listColumn = listOf("Example1", "Example2", "Example3"),
            selectedIndex = 1,
        )
    }
}

