在本博客中，您将学习如何使用具有声明性功能的简单UI组件来创建记事本应用程序。您将不会编辑任何XML布局或使用布局编辑器。相反，您将调用组合函数来定义要使用的元素，然后Compose编辑器将完成其余的工作。该应用程序将使用MVVM架构、Room数据库来在本地保存、列表、更改和删除数据以及用于依赖注入的Hilt&Dagger来开发。

## 一、你会学到什么

- Jetpack Compose UI
- SQLit数据库和SQLite查询语言，实现完整的CRUD
- Android Corrotinas（协程）
- 如何构建应用程序实现MVVM架构

**Jetpack Compose**：是Android推荐的用于创建本机UI的工具包。它简化并加速了Android上的UI开发。

**Composable Preview**：通过`@Composable`函数中的`@Preview`注解，在Android Studio的Split或design选项卡中，可以预览创建的组件

**迭代模式**：在此工具中，可以与Android Studio本身的可视化进行交互并测试不同的功能，例如更改单选按钮的状态，而无需模拟器；

**部署预览**：在此功能中，可以直接在模拟器中通过单击“运行”选项中的模拟器图标来运行`@Preview`，除了更可信的模拟之外，还可以使用权限和常规的应用程序的上下文。

**组合函数**：这些函数允许您以编程方式定义应用程序的UI，描述其形状和数据依赖性，而不是专注于构建UI的过程（初始化元素、将其附加到父元素等）。要创建可组合函数，只需将`@Composable`注释添加到函数名称即可。

**MVVM架构组件**：查看一个简单的图标，其中展示了架构的组件以及它们如何协同工作。

<p align=center><img src="https://github.com/Wisdozzh/JetNotes/blob/main/images/architure.png" alt="image.png"  /></p>

**实体**：使用Room是描述数据库表的带注释的类。

**Room数据库**：简化数据库的使用并充当SQLite数据库的访问点（隐藏SQLiteOpenHelper）。Room数据库使用DAO对SQLite数据库执行查询。

**SQLite数据库**：设备上存储。Room持久性库为您创建并维护该数据库。

**存储库**：您创建的一个类，主要用于管理多个数据源。

**DAO**：数据访问对象。SQL查询到函数的映射。使用DAO时，您可以调用方法，Room会完成剩下的工作。

**LiveData**：数据存储类。它始终维护/换粗年最新版本的数据，并在数据发生变化时通知观察者。

**ViewModel**：充当存储库（数据）和UI之间的通信中心。UI不再需要担心书库来自哪里。ViewModel实例可以在activity/fragment重新创建后继续存在。

**创建包来组织应用程序**

<p align=center><img src="https://github.com/Wisdozzh/JetNotes/blob/main/images/package.png" alt="image.png"  /></p>

## 二、环境搭建
在开始之前，配置开发环境很重要。确保Android Studio已安装并正确配置以进行Android开发。您的项目中还需要以下依赖项：

- Kotlin：Android开发的官方编程语言
- Jetpack Compose：用于创建UI的现代库
- Room：用于使用本地数据库的持久性库

打开builg.gradle(Module.app)
```
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}
```

```

android {
    namespace = "com.example.jetnotes"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jetnotes"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.14-SNAPSHOT")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Jetpack Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")
    // Room components
    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.51")
    kapt("com.google.dagger:hilt-compiler:2.51")

    // icons
    implementation("androidx.compose.material:material-icons-extended:1.6.2")
    // swipe
    implementation("me.saket.swipe:swipe:1.3.0")
}
}
```

在build.gradle文件（项目：JetNotes）中如下所示：
```
plugins {
    id("com.android.application") version "8.2.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}
```

## 三、创建实体（模型）
该模型负责应用程序的业务逻辑和数据。在此示例中，我们将使用Room创建本地数据库并定义`NoteModel`实体：

创建一个名为`NodeModel`的新Kotlin类文件，其中包含数据类。此类描述实体（代表SQLite表）。类中的每个属性代表表中的一列。Room将使用这些属性来创建表并实例化数据库中的行对象。

下面为代码：
```
package com.example.jetnotes.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(// represents the table in the database
    // name of the table in the database
    tableName = "tb_note",
    indices = [
        Index("title", unique = true)// Bank rule not to register equal securities
    ]
)
data class NoteModel(
    @PrimaryKey(autoGenerate = true)// primary key
    @ColumnInfo(name = "id")// Annotation specifies the name for the table in the SQLite database, If you want to change it
    val id: Int = 0,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String
)
```

让我们看看这些注释的作用：
- `@Entity(table = "tb_note")` 每个`@Entity`类代表一个SQLite表。
- `@PrimaryKey`每个实体都需要一个主键。简而言之，每个单词都有自己的主见。
- `@ColumnInfo(name = "id")`如果您希望表中列的名称与成员变量名称不同，则指定该列的名称。这样，该列的名称将是“id”、“title”、“description”

## 四、从应用程序创建DAO（数据访问对象）
它是任何设计数据持久性的应用程序的基本部分。它充当应用程序业务逻辑和数据库之间的抽象层，促进数据的创建、读取、更新和删除（CRUD）。

对于常见的数据库操作，Room哭提供了便捷的注释，例如`@Insert`、`@Delete`和`@Update`。`@Query`注释用于其他所有内容。您可以对SQLite支持的任何查询进行编程。

注意：DAO必须是接口或抽象类。
```
package com.example.jetnotes.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.jetnotes.data.model.NoteModel
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    // List Notes
    @Query("SELECT * FROM tb_note ORDER BY id ASC")
    fun getAllNotes(): Flow<List<NoteModel>>
    // Select Annotation
    @Query("SELECT * FROM tb_note WHERE id=:noteID")
    fun selectNoteID(noteID: Int): Flow<NoteModel>?
    // Insert Notes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteModel: NoteModel)
    // Update Annotation
    @Update
    suspend fun updateNote(noteModel: NoteModel)
    // Delete Annotation
    @Delete
    suspend fun deleteNote(noteModel: NoteModel)
}
```

注意：使用Flow或LiveData作为返回类型将确保每当数据库中的数据发生更改时都会发送通知。

## 五、实现数据库
Room数据库类必须是抽象的并扩展`RoomDatabase`。通常，您的整个应用程序只需要一个Room数据库实例。

在Room创建数据库之前，`@Database`注释需要几个参数。
- 将NoteModel指定为唯一具有实体列表的类。
- 将版本设置为1.每当数据库表架构发生更改时，您需要增加版本号。
- 将`exportSchema`设置为false以不保留架构版本历史记录备份。
  代码如下：
```
package com.example.jetnotes.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetnotes.data.model.NoteModel

// The @Database annotation requires arguments so that Room can create the database
// After list the database entities and the version number
@Database(
    // list App entities
    entities = [
        NoteModel::class
    ],
    // database version
    version = 1,
    // export DB declare with false to not keep DB
    exportSchema = false
)
abstract class NoteDatabase: RoomDatabase() {
    // function to return NoteDao
    abstract fun noteDao(): NoteDao
}
```

## 六、创建数据库实例
让我们创建一个`NoteModule`对象，定义一个带有数据库构建器所需的`Context`参数的`getNoteDb()`方法。
让我们创建一个返回`getNoteDao`类型的方法。
```
package com.example.jetnotes.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module// provide instance of certain type
@InstallIn(SingletonComponent::class)// inform in which Android class each module will be used or replaced
object NoteModule {
    @Provides
    @Singleton
    fun getNoteDb(
        @ApplicationContext
        context: Context
    ) = Room.databaseBuilder(
        context = context,
        NoteDatabase::class.java,
        "note.db"
    ).build()
    @Provides
    @Singleton
    fun getNoteDao(db: NoteDatabase) = db.noteDao()
}
```
## 七、Hilt应用类
所有使用Hilt的应用程序都必须包含使用`@HiltAndroidApp`注解的Application类。

`@HiltAndroidApp`触发Hilt代码生成，包括用作应用程序级依赖项容器的应用程序基类。
```
package com.example.jetnotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NoteApplication: Application() 
```
生成的Hilt组件附加到Appliacation对象的生命周期并为其提供依赖项。此外，它是应用程序的父组件，这意味着其他组件可以访问提供的依赖项。

## 八、将依赖项注入到应用程序的主类中
一旦在`Application`类中配置了`Hilt`并且应用程序级组件可用，它就可以为具有`@AndroidEntryPoint`注解的其他Android类提供依赖项：

**MainActivity**
```
package com.example.jetnotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jetnotes.ui.screens.HomeScreen
import com.example.jetnotes.ui.theme.JetNotesTheme
import com.example.jetnotes.viewmodels.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetNotesTheme {
                navHostController = rememberNavController()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenMain(navHostController, noteViewModel)
                }
            }
        }
    }
}

@Composable
private fun ScreenMain(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    NavHost(navController = navHostController, startDestination = "home_screen") {
        composable("home_screen") {
            HomeScreen(navHostController = navHostController, noteViewModel = noteViewModel)
        }
        composable("note_screen") {
            NoteScreen(navController = navHostController, noteViewModel = noteViewModel, selected = null)
        }
        composable("note_screen/{note_id}", arguments = listOf(
            navArgument("note_id") {
                type = NavType.IntType
            }
        )) {
            val selected = it.arguments?.getInt("note_id")
            NoteScreen(navController = navHostController, noteViewModel = noteViewModel, selected = selected)
        }
    }
}
```
## 九、创建Repository
Repository类抽象了多个数据源的访问。该Repository不是架构组件库的一部分，但它是代码分离和架构的最佳实践。Repository类提供了一个干净的API，用于访问应用程序其余部分中的数据。

**为什么要使用Repository**
Repository管理查询并允许您使用多个后端。在最常见的示例中，Repository实现逻辑来决定是从网络获取数据还是使用本地数据库中缓存的结果。

**如何实现Repository**
创建一个名为`NoteRepo`的Kotlin类文件并将以下代码粘贴到其中：
```
package com.example.jetnotes.repository

import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.data.room.NoteDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepo @Inject constructor(
    private val noteDao: NoteDao
) {
    fun getAllNotes(): Flow<List<NoteModel>> {
        return noteDao.getAllNotes()
    }
    fun selectNoteID(noteID: Int): Flow<NoteModel?> {
        return noteDao.selectNoteID(noteID)
    }
    suspend fun insertNote(note: NoteModel) {
        return noteDao.insertNote(note)
    }
    suspend fun updateNote(note: NoteModel) {
        return noteDao.updateNote(note)
    }
    suspend fun deleteNote(note: NoteModel) {
        return noteDao.deleteNote(note)
    }
}
```

## 十、Application Sealed Class
让我们创建Kotlin密封类并使用它们来管理应用程序的状态。

密封类的子类可以有多个实例。这允许密封类的对象包含状态。在这些情况下可能的状态：stopped、loading、success和error。

```
package com.example.jetnotes.uitl

sealed class ResultState<out T> {
    object Idle: ResultState<Nothing>()
    object Loading: ResultState<Nothing>()
    data class Success<out T>(val data: T): ResultState<T>()
    data class Error(val exception: Throwable): ResultState<Nothing>()
}
```

## 十一、创建ViewModel
ViewModel充当模型和驶入之间的中介。他负责向View提供必要的数据并处理用户的操作。

ViewModel封账了数据表示和转换逻辑，允许视图与底层业务逻辑无关。

它可以包含可观察的属性，这些属性在数据更改时通知视图，从而实现高效的数据绑定。

**viewModelScope**是一个预定义的`CoroutineScope`，包含在KTX ViewModel扩展中。所有协程必须在一个范围内运行。
`CoroutineScope`管理一个或多个相关的协程。

**launch**是一个创建协程并将功能体的执行发送给相应代理的函数。`DIspatchers.IO`指示该协程应在为I/O操作保留的线程中运行。

**HiltViewModel**是Hilt特定的注释，可用于标记`ViewModel`类。它允许以透明的方式将依赖注入到ViewModel中，这就是我们将在这个项目中使用的。
```
package com.example.jetnotes.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.repository.NoteRepo
import com.example.jetnotes.uitl.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepo: NoteRepo
): ViewModel(){
    val id: MutableState<Int> = mutableIntStateOf(0)
    val title: MutableState<String> = mutableStateOf("")
    val description: MutableState<String> = mutableStateOf("")

    private val _getNotes = MutableStateFlow<ResultState<List<NoteModel>>>(ResultState.Idle)
    val getNotes: StateFlow<ResultState<List<NoteModel>>> = _getNotes

    private val _selectedNote: MutableStateFlow<NoteModel?> = MutableStateFlow(null)
    val selectedNote: StateFlow<NoteModel?> = _selectedNote

    fun getNotes() {
        viewModelScope.launch {
            val result = try {
                noteRepo.getAllNotes().collect {
                    _getNotes.value = ResultState.Success(it)
                }
            } catch (error: Exception) {
                ResultState.Error(error)
            }
            Log.d("RESULT_STATE", "$result")
        }
    }

    private fun insertNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NoteModel(
                title = title.value,
                description = description.value
            )
            noteRepo.insertNote(note)
        }
    }

    private fun updateNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NoteModel(
                id = id.value,
                title = title.value,
                description = description.value
            )
            noteRepo.updateNote(note)
        }
    }

    private fun deleteNote() {
        viewModelScope.launch(Dispatchers.IO) {
            val note = NoteModel(
                id = id.value,
                title = title.value,
                description = description.value
            )
            noteRepo.deleteNote(note)
        }
    }

    fun getSelectNoteID(noteID: Int) {
        viewModelScope.launch {
            noteRepo.selectNoteID(noteID).collect {
                _selectedNote.value = it
            }
        }
    }

    fun updateNotesFields(selectedNote: NoteModel?) {
        if (selectedNote != null) {
            id.value = selectedNote.id
            title.value = selectedNote.title
            description.value = selectedNote.description
        } else {
            // clear all fields
            id.value = 0
            title.value = ""
            description.value = ""
        }
    }
    
    fun validateFields(): Boolean {
        return title.value.isNotEmpty() && description.value.isNotEmpty()
    }
    
    fun dbHandle(action: String): String {
        var result = action
        when(result) {
            "INSERT" -> insertNote()
            "UPDATE" -> updateNote()
            "DELETE" -> deleteNote()
            else -> { result = "NO_EVENT" }
        }
        return result
    }
}
```
## 十二、创建App HomeView或App MainScreen
视图是应用程序的表示层，负责向用户显示数据并捕获用户交互，例如taps、clicks和gestures。

它不应包含业务逻辑，而应侧重于直观地表示应用程序数据。
视图通常由用户界面组件组成，例如按钮、文本输入框、列表和图标。
```
package com.example.jetnotes.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.example.jetnotes.R
import com.example.jetnotes.ui.components.HomeContent
import com.example.jetnotes.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHostController: NavHostController, noteViewModel: NoteViewModel) {
    LaunchedEffect(key1 = Unit) {
        noteViewModel.getNotes()
    }

    val getAllNotes by noteViewModel.getNotes.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth(),
                title = {
                    Text(stringResource(id = R.string.home_app_bar), fontWeight = FontWeight.Bold)
                })
        },
        content = {it
            HomeContent(
                modifier = Modifier.padding(it),
                getAllNotes = getAllNotes,
                navController = navHostController,
                onDelete = { event, note ->
                    noteViewModel.dbHandle(event)
                    noteViewModel.updateNotesFields(note)
                })
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate("note_screen") {
                        popUpTo("note_screen") {
                            inclusive = true
                        }
                    }
                }
            ) {
                Icon(Icons.Default.Add, "Floating action button.")
            }
        }
    )
}
```

## 十三、创建查看主页内容
HomeContent是一个用户界面组件，代表主屏幕上的项目。它旨在从打开应用程序的那一刻起就提供信息丰富的用户体验。它可以包含多种元素，例如：

内容列表：在AppBar下方，HomeContent通常会显示与主屏幕相关的内容列表。这可能包括新闻、状态更新、特色产品或对用户重要的任何其他类型的信息。

操作按钮：HomeContent可能包含操作按钮或图标，允许用户执行特定任务、删除、更新、创建新项目或访问应用程序的特定区域。

```
package com.example.jetnotes.ui..screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.jetnotes.data.model.NoteModel
import com.example.jetnotes.ui.theme.ALL_PADDING
import com.example.jetnotes.ui.theme.CARD_SHAPE
import com.example.jetnotes.ui.theme.ICON_SWIPE
import com.example.jetnotes.ui.theme.ICON_THRESHOLD
import com.example.jetnotes.ui.theme.Purple80
import com.example.jetnotes.ui.theme.PurpleGrey40
import com.example.jetnotes.ui.theme.TEXT_DEFAULT
import com.example.jetnotes.ui.theme.TITLE
import com.example.jetnotes.uitl.ResultState
import kotlinx.coroutines.launch
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@Composable
fun HomeContent(
    modifier: Modifier,
    getAllNotes: ResultState<List<NoteModel>>,
    navController: NavController,
    onDelete: (String, NoteModel) -> Unit
) {
    val listNotes: List<NoteModel>

    if (getAllNotes is ResultState.Success) {
        listNotes = getAllNotes.data
        if (listNotes.isEmpty()) {
            EmptyContent()
        } else {
            LazyColumn(  
                modifier = modifier  
            ){
                items(listNotes) {note ->
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(all = ALL_PADDING),
                        shape = RoundedCornerShape(size = CARD_SHAPE)
                    ) {
                        SwipeNote(note = note, navController = navController) {
                            onDelete(
                                "DELETE",
                                note
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeNote(
    note: NoteModel,
    navController: NavController,
    onSwipe: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val deleteSwipe = SwipeAction(
        onSwipe = {
            scope.launch {
                onSwipe()
            }
        },
        icon = {
            Icon(
                modifier = Modifier
                    .padding(ALL_PADDING)
                    .size(ICON_SWIPE),
                imageVector = Icons.Default.DeleteForever,
                contentDescription = "",
                tint = Color.White
            )
        },
        background = Color.Red
    )
    val updateSwipe = SwipeAction(
        onSwipe = {
            navController.navigate("note_screen/${note.id}")
        },
        icon = {
            Icon(
                modifier = Modifier
                    .padding(ALL_PADDING)
                    .size(ICON_SWIPE),
                imageVector = Icons.Default.EditNote,
                contentDescription = "",
                tint = Color.White
            )
        },
        background = PurpleGrey40
    )
    SwipeableActionsBox(
        swipeThreshold = ICON_THRESHOLD,
        startActions = listOf(updateSwipe),
        endActions = listOf(deleteSwipe)
    ) {
        NoteItem(note)
    }
}

@Composable
fun NoteItem(note: NoteModel) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Purple80)
            .padding(all = ALL_PADDING)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = note.title, fontSize = TITLE, fontWeight = FontWeight.Bold)
            if (expanded) {
                Text(text = note.description, fontSize = TEXT_DEFAULT)
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                contentDescription = if (expanded) {
                    "Menos"
                }else { "Mais" }
            )
        }
    }
}
```
**创建注册视图**
```
package com.example.jetnotes.ui.screens.note

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.jetnotes.viewmodels.NoteViewModel

@Composable
fun NoteScreen(
    navController: NavController,
    noteViewModel: NoteViewModel,
    selected: Int?
) {
    selected?.let {  
        noteViewModel.getSelectNoteID(selected)  
        noteViewModel.updateNotesFields(noteViewModel.selectedNote.value)  
    }
    var title by noteViewModel.title
    var description by noteViewModel.description

    Scaffold(
        topBar = {
            if (selected == null) {
                CreateNoteTopBar(navController = navController, noteViewModel = noteViewModel)
            } else {
                UpdateNoteTopBar(navController = navController, noteViewModel = noteViewModel)
            }
        }
    ) { it ->
        NoteContent(
            modifier = Modifier.padding(it),
            title = title,
            onTitle = { title = it},
            description = description,
            onDescription = { description = it }
        )
    }
}
```
**创建笔记内容**
```
package com.example.jetnotes.ui.screens.note

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.jetnotes.R
import com.example.jetnotes.ui.theme.ALL_PADDING
import com.example.jetnotes.ui.theme.SPACER_HEIGHT
import com.example.jetnotes.ui.theme.TEXT_DEFAULT
import com.example.jetnotes.ui.theme.surface

@Composable
fun NoteContent(
    modifier: Modifier,
    title: String,
    onTitle: (String) -> Unit,
    description: String,
    onDescription: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(surface)
            .padding(ALL_PADDING)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = {onTitle(it)},
            label = { Text(text = stringResource(id = R.string.note_title), fontSize = TEXT_DEFAULT) },
            placeholder = { Text(text = stringResource(id = R.string.place_holder_note_title), fontSize = TEXT_DEFAULT)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(SPACER_HEIGHT))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = description, 
            onValueChange = {onDescription(it)},
            label = { Text(text = stringResource(id = R.string.note_description), fontSize = TEXT_DEFAULT) },
            placeholder = { Text(text = stringResource(id = R.string.place_holder_note_description), fontSize = TEXT_DEFAULT)},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions ( 
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
    }
}
```

## 十四、从应用程序创建NoteTopApp
屏幕顶部通常包含一个应用程序栏，其中可能包含应用程序Logo和标题，以及搜索或菜单等操作按钮。

```
package com.example.jetnotes.ui.screens.note

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.jetnotes.R
import com.example.jetnotes.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteTopBar(navController: NavController, noteViewModel: NoteViewModel) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(id = R.string.create_app_bar)) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("home_screen") {
                    popUpTo("home_screen") {
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.icon_arrow_back)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                if (noteViewModel.validateFields()) {
                    val insertResult = noteViewModel.dbHandle("INSERT")
                    if (insertResult == "INSERT") {
                        Toast.makeText(context, "Recorded successfully!!", Toast.LENGTH_LONG).show()
                        navController.navigate("home_screen")
                    } else {
                        Toast.makeText(context, "Recording error!!", Toast.LENGTH_LONG).show()
                    }
                    Log.d("RESULTINSERT", insertResult)
                } else {
                    Toast.makeText(context, "Fill in the fields!!", Toast.LENGTH_LONG).show()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(id = R.string.icon_check)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateNoteTopBar(navController: NavController, noteViewModel: NoteViewModel) {
    val context = LocalContext.current
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = stringResource(id = R.string.update_app_bar)) },
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("home_screen") {
                    popUpTo("home_screen") {
                        inclusive = true
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.icon_arrow_back)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                if (noteViewModel.validateFields()) {
                    val updateResult = noteViewModel.dbHandle("UPDATE")
                    if (updateResult == "UPDATE") {
                        Toast.makeText(context, "Update successfully!!", Toast.LENGTH_LONG).show()
                        navController.navigate("home_screen")
                    } else {
                        Toast.makeText(context, "Recording error!!", Toast.LENGTH_LONG).show()
                    }
                    Log.d("RESULT_UPDATE", updateResult)
                } else {
                    Toast.makeText(context, "Filled in the fields!!", Toast.LENGTH_LONG).show()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.EditNote,
                    contentDescription = stringResource(id = R.string.icon_check)
                )
            }
        }
    )
}
```
## 十五、创建EmptyContent
我们将开发UI组件，作为应用程序屏幕特定部分中缺失内容的标记。当没有要显示的数据或者列表或部分为空时，将使用它。
```
package com.example.jetnotes.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.jetnotes.R
import com.example.jetnotes.ui.theme.ICON_EMPTY
import com.example.jetnotes.ui.theme.Purple40
import com.example.jetnotes.ui.theme.surface

@Composable
fun EmptyContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(surface),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(ICON_EMPTY),
            imageVector = Icons.Default.SentimentVeryDissatisfied,
            contentDescription = stringResource(id = R.string.icon_empty),
            tint = Purple40
        )
        Text(
            text = "\n\n" + stringResource(id = R.string.text_empty)
        )
    }
}
```

下面是演示效果:

<p align=center><img src="https://github.com/Wisdozzh/JetNotes/blob/main/images/preview.gif" alt="preview.gif" width="70%" /></p>



[Github链接：JetNotes](https://github.com/Wisdozzh/JetNotes/tree/main)

## 十六、结论
使用干净的代码、MVVM架构、Kotlin、Jetpack Compose和Android Studio进行Android应用开发时创建满足用户期望和高质量应用程序的有效方法。采用良好的开发实践，包括关注点分离、测试和充足的文档，有助于维护干净、可维护的代码。通过遵循这些实践，您可以创建更高效、可扩展并且随着应用程序的发展和发展而更易于维护的应用程序。
