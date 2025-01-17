import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anmp.myapplication.model.AuthorDAO
import com.anmp.myapplication.model.HobbyDatabase
import com.anmp.myapplication.model.News
import com.anmp.myapplication.model.NewsDAO
import com.anmp.myapplication.util.buildDb
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class NewsViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {

    val newsLiveData = MutableLiveData<List<News>>()
    private var queue: RequestQueue? = null
    val newsLoadErrorLD = MutableLiveData<Boolean>()
    val loadingNews = MutableLiveData<Boolean>()
    private val newsDao:NewsDAO= buildDb(application).newsDao()
    private val authorDao:AuthorDAO= buildDb(application).authorDao()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

//    fun refresh() {
//        newsLoadErrorLD.value = true
//        loadingNews.value = false
//        launch {
//            newsLiveData.postValue(newsDao.getAllNews())
//            val newsList = newsDao.getAllNews()
//            newsList.forEach { news ->
//                val authorId = news.authorId
//                val author = authorId?.let { authorDao.getAuthorById(it) }
//                author?.let {
//                    Log.d("author", author.toString())
//                }
//            newsLoadErrorLD.postValue(false)
//        }
//    }}

    fun refresh() {
        newsLoadErrorLD.value = false
        loadingNews.value = true
        launch {
            try {
                val newsList = newsDao.getAllNews()
                newsList.forEach { news ->
                    val authorId = news.authorId
                    val author = authorId?.let { authorDao.getAuthorById(it) }
                    author?.let {
                        news.authorName = it.name
                    }

                }
                newsLiveData.postValue(newsList)
                loadingNews.postValue(false)
            } catch (e: Exception) {
                newsLoadErrorLD.postValue(true)
                loadingNews.postValue(false)
            }
        }
    }

    fun inputNews() {
        launch {
            newsDao.insertAll(dataNews())
        }
    }

    private fun dataNews(): List<News> {
        return listOf(
            News(
                id=1,
                imageUrl = "https://cdn2.thecatapi.com/images/MjAxMDc4OA.jpg",
                title = "The Playful Nature of Kittens",
                username = "kitten_enthusiast",
                synopsis = "Explore the adorable world of kittens and learn about their playful antics.",
                description = "Kittens are bundles of joy with boundless energy and a natural curiosity that makes them endlessly entertaining. In this insightful article, we delve into the playful nature of kittens, unravelling the reasons behind their charming behavior. From their enthusiastic pouncing to their love for exploring new toys, kittens exhibit a wide array of adorable antics that bring delight to cat lovers everywhere.Kittens' playfulness serves a crucial role in their development and socialization. Through play, kittens refine their motor skills, coordination, and agility. It's not uncommon to see a kitten engaging in acrobatic leaps or engaging in mock hunting behaviors with a piece of string. This play mimics real-life situations and helps kittens develop essential skills that they will carry into adulthood. Beyond physical development, play is also vital for kittens' mental stimulation and emotional well-being. It allows them to relieve stress, express themselves, and build confidence in their abilities. As kittens interact with their environment and engage in play with littermates or human companions, they learn valuable social skills and develop strong bonds with those around them. Kittens' playful antics are simply irresistible. Whether it's chasing after a feather toy, batting at a ball, or performing unexpected acrobatics, kittens bring joy and laughter into our lives. Their playful nature not only enriches our own experiences but also contributes to their happiness and overall development.",
                date = "2024-04-17",
                category = "Animal, Cat",
                authorId = 1
            ),
            News(
                id=2,
                imageUrl = "https://cdn2.thecatapi.com/images/5pq.jpg",
                title = "Understanding Cat Behavior",
                username = "cat_behavior_specialist",
                synopsis = "Learn about the fascinating behaviors exhibited by cats and what they communicate.",
                description = "Cats are enigmatic creatures with behaviors that often leave us intrigued and mystified. In this comprehensive article, we explore the complexities of cat behavior, shedding light on the meanings behind their actions. From kneading and purring to hunting instincts and social dynamics, understanding cat behavior is key to fostering a deeper connection with our feline companions. One of the most common behaviors seen in cats is kneading, where they rhythmically push their paws against a soft surface. This behavior is often associated with feelings of contentment and relaxation, reminiscent of the kneading motions kittens make while nursing. Understanding this behavior can help us recognize when our cats are feeling calm and comfortable. Purring is another fascinating behavior exhibited by cats. While purring is commonly thought of as a sign of happiness, cats may also purr when they are stressed, in pain, or seeking comfort. By paying attention to the context and accompanying body language, we can better interpret what our cats are trying to communicate through their purrs. Hunting instincts are deeply ingrained in cats, even those who have never set foot outdoors. Cats may display stalking behaviors, such as crouching low and silently observing their surroundings, before pouncing on a toy or unsuspecting object. Providing opportunities for play that simulate hunting can satisfy these natural instincts and keep our indoor cats mentally and physically stimulated. By gaining insights into the mysterious world of cat behavior, we can forge stronger bonds with our feline friends and ensure their well-being. Whether you're a seasoned cat owner or considering adopting a cat for the first time, understanding cat behavior is essential for creating a harmonious and fulfilling relationship with these remarkable animals.",
                date = "2024-04-16",
                category = "Animal",
                authorId = 1
            ),
            News(
                id = 3,
                imageUrl = "https://cdn2.thecatapi.com/images/ddi.jpg",
                title = "The Beauty of Tabby Cats",
                username = "tabby_cat_lover",
                synopsis = "Discover the charm and distinctive markings of tabby cats.",
                description = "Tabby cats are among the most beloved and recognizable feline companions, celebrated for their striking coat patterns and unique personalities. In this captivating article, we delve into the beauty of tabby cats, exploring their genetic traits, historical significance, and enduring appeal. The term 'tabby' refers to a coat pattern rather than a specific breed, encompassing a range of patterns from classic stripes to swirling marbles and spotted tabbies. Each tabby cat's markings are as unique as a fingerprint, reflecting their genetic heritage and individuality. Tabby cats have a storied history, with depictions of tabby-like cats dating back centuries. Their distinctive markings have inspired artists, writers, and cat enthusiasts throughout the ages. From ancient Egypt to modern times, tabby cats have held a special place in our hearts and culture. Aside from their aesthetics, tabby cats are cherished for their friendly and adaptable personalities. They often possess a playful and curious nature, making them wonderful companions for families and individuals alike. Tabby cats form strong bonds with their human caregivers and thrive on affection and interaction. Whether you're already smitten with a tabby cat or simply intrigued by their charm, this article will deepen your appreciation for these captivating feline companions. Join us as we celebrate the beauty and allure of tabby cats, highlighting their role as beloved members of our homes and hearts.",
                date = "2024-04-15",
                category = "Cat",
                authorId = 2
            ),
            News(
                id = 4,
                imageUrl = "https://cdn2.thecatapi.com/images/EPF2ejNS0.jpg",
                title = "The Mythology of Black Cats",
                username = "black_cat_enthusiast",
                synopsis = "Uncover the myths and legends surrounding black cats throughout history.",
                description = "Black cats have long been shrouded in mystery and superstition, their dark fur and piercing eyes evoking a mix of fear and fascination. In this enlightening article, we unravel the myths and legends associated with black cats, exploring their symbolism across different cultures and time periods. In many cultures, black cats have been both revered and feared. In ancient Egypt, they were considered sacred and were believed to bring good fortune. However, during the Middle Ages in Europe, black cats became associated with witchcraft and were often persecuted alongside their human counterparts. Despite their unjust reputation, black cats have also been viewed as symbols of protection and good luck in various cultures. Sailors believed that having a black cat aboard a ship would ensure safe passage, while Japanese folklore associates black cats with warding off evil spirits. Today, black cats continue to be beloved companions, cherished for their unique personalities and sleek appearance. Whether they're curled up by the fire or prowling through moonlit gardens, black cats embody a sense of mystery and grace that captivates our imaginations. By exploring the mythology of black cats, we gain a deeper understanding of how these enigmatic creatures have influenced human culture and folklore. Whether you're a black cat enthusiast or simply curious about their storied past, this article invites you to appreciate the beauty and resilience of these remarkable feline companions.",
                date = "2024-04-14",
                category = "Animal",
                authorId = 2
            ),
            News(
                id = 5,
                imageUrl = "https://cdn2.thecatapi.com/images/bpd.jpg",
                title = "The Elegance of Persian Cats",
                username = "persian_cat_fanatic",
                synopsis = "Admire the luxurious beauty and gentle nature of Persian cats.",
                description = "Persian cats are synonymous with luxury, known for their long, flowing coats and sweet, docile personalities. In this enchanting article, we embark on a journey into the world of Persian cats, uncovering their origins, grooming needs, and endearing characteristics. Originating from Persia (modern-day Iran), Persian cats have a rich history dating back centuries. They were favored by aristocrats and royalty for their regal appearance and calm demeanor. Over time, Persian cats have become one of the most popular and recognizable breeds worldwide. One of the defining features of Persian cats is their luxurious coat, which requires regular grooming to keep it tangle-free and lustrous. Persian cats are known for their calm and gentle disposition, making them ideal companions for households seeking a laid-back pet. Despite their glamorous appearance, Persian cats are not high-energy cats and prefer a quiet environment. They enjoy lounging in comfortable spots and receiving gentle affection from their human companions. Their expressive eyes and affectionate nature endear them to cat lovers of all ages.",
                date = "2024-04-13",
                category = "Cat",
                authorId = 3
            ),
            News(
                id = 6,
                imageUrl = "https://cdn2.thecatapi.com/images/GwRBXx7-w.jpg",
                title = "The Science of Cat Naps",
                username = "cat_sleep_researcher",
                synopsis = "Explore the benefits of cat naps and how cats achieve optimal sleep.",
                description = "Cats are renowned for their ability to nap throughout the day, often seeming to achieve peak relaxation with minimal effort. In this enlightening article, we delve into the science behind cat naps, uncovering why cats sleep so much, the stages of feline sleep, and the importance of rest for their well-being. Cats are obligate carnivores, which means their bodies are adapted to derive energy efficiently from meat. As a result, cats conserve energy by sleeping for extended periods, typically ranging from 12 to 16 hours a day. This behavior is rooted in their evolutionary history as solitary hunters. Feline sleep is composed of two main stages: REM (rapid eye movement) sleep and non-REM sleep. During REM sleep, cats may exhibit twitching or paw movements, indicating intense brain activity. Non-REM sleep, on the other hand, is characterized by deep relaxation and muscle repair. Cat naps serve multiple purposes beyond simple rest. They allow cats to conserve energy for hunting, regulate body temperature, and process information acquired during wakeful periods. Cats are crepuscular animals, meaning they are most active during twilight hours, making cat naps essential for maintaining their natural rhythms. By understanding the science behind cat naps, we gain valuable insights into our cats' health and behavior. Providing a conducive environment for quality sleep, including cozy sleeping spots and regular play sessions, enhances our cats' overall well-being and strengthens our bond with these graceful creatures.",
                date = "2024-04-12",
                category = "Animal,Cat,Cute",
                authorId = 3
            ),
            News(
                id = 7,
                imageUrl = "https://cdn2.thecatapi.com/images/1om.jpg",
                title = "Siamese Cats: Graceful and Vocal",
                username = "siamese_cat_enthusiast",
                synopsis = "Learn about the distinctive traits and vocal nature of Siamese cats.",
                description = "Siamese cats are strikingly beautiful, with sleek bodies, blue almond-shaped eyes, and a penchant for vocalizing. In this captivating article, we explore the unique characteristics of Siamese cats, from their social personalities to their melodious 'voices' that captivate cat lovers worldwide. Originally from Thailand (formerly Siam), Siamese cats are known for their outgoing and sociable nature. They thrive on interaction with their human companions and are not shy about expressing their opinions through meows and chirps. One of the most distinctive traits of Siamese cats is their vocal nature. Siamese cats are highly communicative and enjoy engaging in 'conversations' with their owners. Each Siamese cat has a unique vocal repertoire, from soft murmurs to enthusiastic yowls, making them adept at getting their point across. Siamese cats form strong bonds with their families and enjoy participating in household activities. They are intelligent cats that respond well to positive reinforcement training and interactive play. Siamese cats thrive in homes where they receive plenty of mental stimulation and companionship. Whether you're already enchanted by Siamese cats or are considering welcoming one into your home, this article provides valuable insights into their charming personalities and communicative nature. Join us as we celebrate the grace and vocal talents of Siamese cats, appreciating their role as beloved companions and cherished family members.",
                date = "2024-04-11",
                category = "Animal,Cat,Cute",
                authorId = 4
            ),
            News(
                id = 8,
                imageUrl = "https://cdn2.thecatapi.com/images/zfVWBciUH.jpg",
                title = "Ragdoll Cats: Gentle Giants",
                username = "ragdoll_cat_admirer",
                synopsis = "Discover the gentle temperament and loving nature of Ragdoll cats.",
                description = "Ragdoll cats are gentle giants renowned for their affectionate demeanor, striking blue eyes, and 'floppy' behavior when picked up. In this endearing article, we explore the unique characteristics of Ragdoll cats and why they are beloved by cat enthusiasts around the world. Originating from California, Ragdoll cats are named for their tendency to go limp and relax completely when held, resembling a child's ragdoll toy. This 'floppy' behavior is a testament to their gentle temperament and trust in their human companions. Ragdoll cats are known for their calm and easy-going nature, making them excellent companions for families and individuals alike. They thrive on affection and enjoy spending time with their human 'pack,' often following them around the house or curling up in their laps. Despite their large size, Ragdoll cats are not particularly active and prefer a laid-back lifestyle. They enjoy lounging on soft surfaces and appreciate having cozy spots to retreat to for naps. Their striking blue eyes and semi-long coat add to their regal appearance. Whether you're already a Ragdoll cat admirer or are considering adopting one, this article offers valuable insights into their gentle temperament and loving nature. Join us as we celebrate the charm and grace of Ragdoll cats, appreciating their role as cherished companions and loyal friends.",
                date = "2024-04-10",
                category = "Animal,Cat,Cute",
                authorId = 4
            ),
            News(
                id = 9,
                imageUrl = "https://cdn2.thecatapi.com/images/9d2.jpg",
                title = "Maine Coon Cats: Majestic and Friendly",
                username = "maine_coon_lover",
                synopsis = "Learn about the largest domestic cat breed and its friendly disposition.",
                description = "Maine Coon cats are often referred to as 'gentle giants' due to their impressive size, tufted ears, and friendly personalities. In this informative article, we delve into the fascinating world of Maine Coon cats, exploring their origins, characteristics, and why they make wonderful family pets. Originally hailing from the state of Maine in the United States, Maine Coon cats are one of the largest domestic cat breeds, with males often weighing up to 18 pounds or more. Despite their size, they are known for their gentle and friendly demeanor. Maine Coon cats are highly sociable and enjoy interacting with their human companions. They are intelligent cats that thrive on mental stimulation and play. Maine Coons are often described as 'dog-like' due to their loyalty and affectionate nature. One of the defining features of Maine Coon cats is their tufted ears and bushy tails, which provide insulation against cold weather. Their thick, water-repellent coat and robust build reflect their adaptation to the harsh climate of the Northeastern United States. Whether you're already a Maine Coon enthusiast or are considering adopting one, this article offers valuable insights into their majestic appearance and friendly disposition. Join us as we celebrate the charm and companionship of Maine Coon cats, appreciating their role as beloved members of the family.",
                date = "2024-04-09",
                category = "Cat",
                authorId = 1
            ),
            News(
                id = 10,
                imageUrl = "https://cdn2.thecatapi.com/images/86g.jpg",
                title = "Ginger Cats: Personality and Charm",
                username = "ginger_cat_aficionado",
                synopsis = "Appreciate the unique traits and vibrant personalities of ginger cats.",
                description = "Ginger cats, also known as orange tabbies, are cherished for their warm coloration and spirited personalities. In this delightful article, we celebrate the charm and charisma of ginger cats, exploring their playful nature, intelligence, and endearing quirks. Ginger cats' distinctive coat color is caused by a genetic variation that results in orange fur. The intensity of their coloration can vary from pale cream to deep reddish-orange, with tabby markings adding to their visual appeal. Personality-wise, ginger cats are often described as outgoing and confident. They are known for their playful antics and boundless energy, making them delightful companions for households seeking an interactive pet. Ginger cats are celebrated for their intelligence and adaptability. They excel in puzzle games and interactive toys, using their keen sense of curiosity to explore their environment. Ginger cats often form strong bonds with their human caregivers and enjoy being included in daily activities. Whether you're already a proud owner of a ginger cat or simply admire their fiery spirit, this article is a delightful ode to these lovable feline companions. Join us as we appreciate the unique traits and vibrant personalities of ginger cats, celebrating their role as beloved members of our families.",
                date = "2024-04-08",
                category = "Cat",
                authorId = 1
            )












        )
    }

//    fun fetchNews() {
//        queue = Volley.newRequestQueue(getApplication())
//        val url = "http://10.0.2.2/anmp/news_list.php?news"
//
//        val stringRequest = StringRequest(
//            Request.Method.GET, url,
//            { response ->
//                try {
//                    val jsonResponse = JSONObject(response)
//                    if (jsonResponse.getString("status") == "success") {
//                        val newsArray = jsonResponse.getJSONArray("data")
//
//                        val newsListType = object : TypeToken<ArrayList<News>>() {}.type
//                        val newsList = Gson().fromJson<ArrayList<News>>(newsArray.toString(), newsListType)
//
//                        newsLiveData.value = newsList
//
//                    } else {
//                        Log.e("NewsViewModel", "Status is not success")
//                    }
//                } catch (e: JsonSyntaxException) {
//                    Log.e("NewsViewModel", "JSON syntax error: ${e.message}")
//                } catch (e: Exception) {
//                    Log.e("NewsViewModel", "Error fetching news: ${e.message}")
//                }
//            },
//            { error ->
//                Log.e("NewsViewModel", "Volley error: ${error.message}")
//            }
//        )
//
//        queue?.add(stringRequest)
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        queue?.cancelAll(TAG)
//    }

    companion object {
        private const val TAG = "NewsViewModel"
    }
}
