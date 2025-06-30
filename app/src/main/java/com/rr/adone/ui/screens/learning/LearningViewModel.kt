package com.rr.adone.ui.screens.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rr.adone.domain.model.ContentItem
import com.rr.adone.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(LearningUiState())
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()
    
    fun loadLearningContent(contentId: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            
            try {
                val content = contentRepository.getContentItem(contentId)
                if (content != null) {
                    val steps = generateLearningSteps(content)
                    _uiState.value = _uiState.value.copy(
                        contentItem = content,
                        learningSteps = steps,
                        totalSteps = steps.size,
                        currentStep = 1,
                        currentStepTitle = steps.firstOrNull()?.title ?: "",
                        currentStepContent = steps.firstOrNull()?.content ?: "",
                        currentCodeExample = steps.firstOrNull()?.codeExample ?: "",
                        isLoading = false,
                        error = null
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Content not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load learning content"
                )
            }
        }
    }
    
    fun nextStep() {
        val currentState = _uiState.value
        if (currentState.currentStep < currentState.totalSteps) {
            val nextStepIndex = currentState.currentStep
            val nextStep = currentState.learningSteps[nextStepIndex]
            _uiState.value = currentState.copy(
                currentStep = currentState.currentStep + 1,
                currentStepTitle = nextStep.title,
                currentStepContent = nextStep.content,
                currentCodeExample = nextStep.codeExample
            )
        }
    }
    
    fun previousStep() {
        val currentState = _uiState.value
        if (currentState.currentStep > 1) {
            val prevStepIndex = currentState.currentStep - 2
            val prevStep = currentState.learningSteps[prevStepIndex]
            _uiState.value = currentState.copy(
                currentStep = currentState.currentStep - 1,
                currentStepTitle = prevStep.title,
                currentStepContent = prevStep.content,
                currentCodeExample = prevStep.codeExample
            )
        }
    }
    
    fun completeLearning() {
        _uiState.value = _uiState.value.copy(isCompleted = true)
    }
    
    private fun generateLearningSteps(content: ContentItem): List<LearningStep> {
        return when (content.category) {
            "Architecture" -> generateArchitectureSteps(content)
            "Programming" -> generateProgrammingSteps(content)
            "UI/UX" -> generateUISteps(content)
            "Data Management" -> generateDataSteps(content)
            "Networking" -> generateNetworkingSteps(content)
            "Testing" -> generateTestingSteps(content)
            "Navigation" -> generateNavigationSteps(content)
            "Performance" -> generatePerformanceSteps(content)
            "Security" -> generateSecuritySteps(content)
            else -> generateGenericSteps(content)
        }
    }
    
    private fun generateArchitectureSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Understanding MVVM Pattern",
                content = "MVVM (Model-View-ViewModel) is an architectural pattern that separates your UI logic from business logic. The Model represents data, View handles UI, and ViewModel acts as a bridge, exposing data to the UI through observable patterns like StateFlow.",
                codeExample = """
class HomeViewModel @Inject constructor(
    private val repository: ContentRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Implementing StateFlow",
                content = "StateFlow is a reactive data holder that emits current state and any subsequent state updates. It's perfect for UI state management as it's lifecycle-aware and provides the latest value to new collectors.",
                codeExample = """
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()
    
    when {
        uiState.isLoading -> LoadingScreen()
        uiState.error != null -> ErrorScreen(uiState.error)
        else -> ContentScreen(uiState.items)
    }
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Repository Pattern",
                content = "The Repository pattern provides a clean API for data access, abstracting the data sources (local database, network, cache). It centralizes data operations and provides a single source of truth.",
                codeExample = """
@Singleton
class ContentRepositoryImpl @Inject constructor(
    private val localDataSource: ContentDao,
    private val remoteDataSource: ApiService
) : ContentRepository {
    
    override fun getContentItems(): Flow<List<ContentItem>> {
        return localDataSource.getAllContentFlow()
    }
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Clean Architecture Benefits",
                content = "This architecture provides separation of concerns, testability, maintainability, and scalability. Each layer has a single responsibility, making your code easier to understand, test, and modify.",
                codeExample = ""
            )
        )
    }
    
    private fun generateProgrammingSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Kotlin Basics",
                content = "Kotlin is a modern, expressive language with null safety, data classes, extension functions, and coroutines for asynchronous programming.",
                codeExample = """
// Data class with automatic equals, hashCode, toString
data class ContentItem(
    val id: String,
    val title: String,
    val description: String
)

// Extension function
fun String.isValidEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Coroutines and Async Programming",
                content = "Coroutines provide a way to write asynchronous, non-blocking code in a sequential manner. They're lightweight threads that can be suspended and resumed.",
                codeExample = """
class Repository {
    suspend fun fetchData(): Result<List<Item>> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getData()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Null Safety",
                content = "Kotlin's type system distinguishes between nullable and non-nullable types, helping prevent NullPointerExceptions at compile time.",
                codeExample = """
// Non-nullable
var name: String = "Android"

// Nullable
var optionalName: String? = null

// Safe call operator
val length = optionalName?.length

// Elvis operator
val nameLength = optionalName?.length ?: 0
                """.trimIndent()
            )
        )
    }
    
    private fun generateUISteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Jetpack Compose Fundamentals",
                content = "Compose is Android's modern UI toolkit for building native UI with declarative syntax. Everything is a function that describes what the UI should look like.",
                codeExample = """
@Composable
fun Greeting(name: String) {
    Text(
        text = "Hello ${'$'}name!",
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.primary
    )
}
                """.trimIndent()
            ),
            LearningStep(
                title = "State Management in Compose",
                content = "Compose uses state to determine what to show in the UI. When state changes, Compose automatically recomposes (re-executes) the parts of the UI that read that state.",
                codeExample = """
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }
    
    Column {
        Text("Count: ${'$'}count")
        Button(onClick = { count++ }) {
            Text("Increment")
        }
    }
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Material 3 Design",
                content = "Material 3 (Material You) is Google's latest design system with dynamic color, updated components, and new foundations for large screens.",
                codeExample = """
MaterialTheme(
    colorScheme = dynamicColorScheme(LocalContext.current),
    typography = Typography,
    content = content
)
                """.trimIndent()
            )
        )
    }
    
    private fun generateDataSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Room Database Setup",
                content = "Room is a persistence library that provides an abstraction layer over SQLite. It consists of Database, DAO (Data Access Object), and Entity components.",
                codeExample = """
@Entity(tableName = "content_items")
data class ContentItem(
    @PrimaryKey val id: String,
    val title: String,
    val description: String
)

@Dao
interface ContentDao {
    @Query("SELECT * FROM content_items")
    fun getAllContent(): Flow<List<ContentItem>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContent(item: ContentItem)
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Offline-First Architecture",
                content = "Offline-first means your app works without internet by storing data locally and syncing when connected. This provides better user experience and performance.",
                codeExample = """
class Repository @Inject constructor(
    private val localDao: ContentDao,
    private val apiService: ApiService
) {
    fun getContent(): Flow<List<ContentItem>> = localDao.getAllContent()
    
    suspend fun refresh() {
        try {
            val remoteData = apiService.getContent()
            localDao.insertAll(remoteData)
        } catch (e: Exception) {
            // Handle offline gracefully
        }
    }
}
                """.trimIndent()
            )
        )
    }
    
    private fun generateNetworkingSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Ktor Client Setup",
                content = "Ktor is a modern HTTP client for Kotlin with coroutines support, content negotiation, and powerful features for API communication.",
                codeExample = """
val client = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json()
    }
    install(Logging) {
        level = LogLevel.INFO
    }
}
                """.trimIndent()
            ),
            LearningStep(
                title = "Making API Calls",
                content = "Use Ktor's suspend functions to make asynchronous HTTP requests. Handle responses and errors gracefully.",
                codeExample = """
suspend fun getContent(): List<ContentItem> {
    return client.get("https://api.example.com/content").body()
}

suspend fun postContent(item: ContentItem): ContentItem {
    return client.post("https://api.example.com/content") {
        contentType(ContentType.Application.Json)
        setBody(item)
    }.body()
}
                """.trimIndent()
            )
        )
    }
    
    private fun generateTestingSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Unit Testing with JUnit",
                content = "Unit tests verify individual components in isolation. Use JUnit 4 or 5 with Mockito for mocking dependencies.",
                codeExample = """
@Test
fun `when repository returns data, uiState should contain items`() = runTest {
    // Given
    val mockItems = listOf(ContentItem("1", "Test", "Description"))
    `when`(repository.getContentItems()).thenReturn(flowOf(mockItems))
    
    // When
    viewModel.loadContent()
    
    // Then
    assertEquals(mockItems, viewModel.uiState.value.items)
}
                """.trimIndent()
            ),
            LearningStep(
                title = "UI Testing with Compose",
                content = "Test your Compose UI using the testing framework with semantic matchers and assertions.",
                codeExample = """
@Test
fun contentCard_displaysCorrectly() {
    composeTestRule.setContent {
        ContentCard(
            item = testContentItem,
            onClick = { }
        )
    }
    
    composeTestRule
        .onNodeWithText("Test Title")
        .assertIsDisplayed()
}
                """.trimIndent()
            )
        )
    }
    
    private fun generateNavigationSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Navigation Compose Setup",
                content = "Navigation Compose provides type-safe navigation between composables using kotlinx.serialization for arguments.",
                codeExample = """
@Serializable
data class Detail(val contentId: String)

NavHost(navController, startDestination = Home) {
    composable<Home> { HomeScreen() }
    composable<Detail> { backStackEntry ->
        val detail: Detail = backStackEntry.toRoute()
        DetailScreen(detail.contentId)
    }
}
                """.trimIndent()
            )
        )
    }
    
    private fun generatePerformanceSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Compose Performance",
                content = "Optimize Compose performance by understanding recomposition, using remember, and avoiding unnecessary calculations.",
                codeExample = """
@Composable
fun ExpensiveComposable(data: List<Item>) {
    val processedData = remember(data) {
        data.map { processItem(it) } // Only recalculate when data changes
    }
    
    LazyColumn {
        items(processedData) { item ->
            ItemCard(item)
        }
    }
}
                """.trimIndent()
            )
        )
    }
    
    private fun generateSecuritySteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Data Encryption",
                content = "Use Android's security library to encrypt sensitive data and implement secure storage practices.",
                codeExample = """
val masterKey = MasterKey.Builder(context)
    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
    .build()

val sharedPreferences = EncryptedSharedPreferences.create(
    context,
    "secret_shared_prefs",
    masterKey,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)
                """.trimIndent()
            )
        )
    }
    
    private fun generateGenericSteps(content: ContentItem): List<LearningStep> {
        return listOf(
            LearningStep(
                title = "Getting Started",
                content = "Welcome to ${content.title}. This comprehensive guide will take you through all the essential concepts and practical implementations.",
                codeExample = ""
            ),
            LearningStep(
                title = "Core Concepts",
                content = content.description,
                codeExample = ""
            ),
            LearningStep(
                title = "Best Practices",
                content = "Learn the industry best practices and common patterns used by professional Android developers.",
                codeExample = ""
            )
        )
    }
}

data class LearningUiState(
    val contentItem: ContentItem? = null,
    val learningSteps: List<LearningStep> = emptyList(),
    val currentStep: Int = 1,
    val totalSteps: Int = 0,
    val currentStepTitle: String = "",
    val currentStepContent: String = "",
    val currentCodeExample: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCompleted: Boolean = false
)

data class LearningStep(
    val title: String,
    val content: String,
    val codeExample: String = ""
)