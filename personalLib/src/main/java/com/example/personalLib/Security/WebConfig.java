/*package com.example.personalLib.Security;

import com.example.personalLib.DB.Models.*;
import com.example.personalLib.DB.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.h2.server.web.WebServlet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class WebConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {

        UserModel userModel = new UserModel("Alina", "Alina", "12345678", LocalDateTime.now());
        Set<Role> r  = new HashSet<>();
        r.add(Role.USER);
        userModel.setRoles(r);
        userModel.setActive(true);
        userRepository.save(userModel);

        BookModel bookModel = new BookModel("978-5-699-43491-6", "Доктор Живаго", "Знаковый для многих " +
                "поколений, бывший долгие годы под запретом роман \"Доктор Живаго\" повествует о подлинной любви - свободной от" +
                " преград, будоражащей воображение, разрушающей стереотипы, бесповоротно меняющей саму суть человеческого" +
                " существования и не признающей никаких правил. К тому же этот роман стал верным психологическим свидетельством " +
                "эпохи репрессий и притеснений личности. Пастернак был одним из немногих, кто ценой собственной судьбы не " +
                "побоялся выступить в защиту свободы слова и совести.", "1.jpg", 4.10);

        BookModel bookModel2 = new BookModel("978-5-699-39016-8", "Остаток дня", "Урожденный японец, " +
                "выпускник литературного курса Малькольма Брэдбери, написавший самый английский роман конца XХ века!\n" +
                "Дворецкий Стивенс, без страха и упрека служивший лорду Дарлингтону, рассказывает о том, как у него развивалось" +
                " чувство долга и умение ставить нужных людей на нужное место, демонстрируя поистине самурайскую замкнутость в " +
                "рамках своего кодекса служения.\n" +
                "В 1989 г. за \"Остаток дня\" Исигуро единогласно получил Букера (и это было, пожалуй, единственное решение " +
                "Букеровского комитета за всю историю премии, ни у кого не вызвавшее протеста).", "2.jpg", 4.08);

        BookModel bookModel3 = new BookModel("978-5-17-104151-9", "Заводной апельсин", "\"Заводной " +
                "апельсин\" – литературный парадокс ХХ столетия. Продол-жая футуристические традиции в литературе," +
                " экспериментируя с языком, на котором говорит рубежное поколение malltshipalltshikov и kisok \"надсатых\", " +
                "Энтони Бёрджесс создает роман, признанный классикой современной литературы.\n" +
                "Умный, жестокий, харизматичный антигерой Алекс, лидер уличной банды, проповедуя насилие как высокое искусство жизни, " +
                "как род наслаждения, попадает в железные тиски новейшей государственной программы по перевоспитанию преступников и сам " +
                "становится жертвой наси-лия. Можно ли спасти мир от зла, лишая человека воли совершать пос-тупки и превращая его в " +
                "\"заводной апельсин\"? Этот вопрос сегодня актуален так же, как и вчера, и вопрос этот автор задает читателю.", "3.jpg", 4.02);

        AuthorModel authorModel = new AuthorModel("Writer", "Борис Пастернак");
        AuthorModel authorModel2 = new AuthorModel("Writer", "Кадзуо Исигуро");
        AuthorModel authorModel3 = new AuthorModel("Writer", "Энтони Берджесс");

        bookModel.setBookAuthors(Arrays.asList(authorModel));
        bookModel2.setBookAuthors(Arrays.asList(authorModel2));
        bookModel3.setBookAuthors(Arrays.asList(authorModel3));

        GenreModel genre = new GenreModel("Классика");
        GenreModel genre2 = new GenreModel("Русская литература");

        bookModel.setBookgenres(Arrays.asList(genre, genre2));

        ReviewModel review = new ReviewModel(userModel, bookModel, "Очень сложно писать отзыв/рецензию на всемирно известное" +
                " классическое произведение, и тем более Нобелевского лауреата. Пастернак один из моих самых любимых поэтов," +
                " но с его прозой я знакома не была. Мне все казалось, что либо стихи, либо проза, что автор не может писать и " +
                "то и другое одинаково хорошо, а впечатление портить не хотелось. И как же приятно было открыть для себя, что" +
                " этот автор может! Его проза: описание природы, быта, города, невероятно поэтичны! Его гуси, рябина, бескрайние " +
                "леса и поля достойны кисти художника, как и женщины, которые невероятно сильны духом и физически, и весь роман " +
                "пронизан восхищением и любовью к ним. А вот мужчины у Пастернака не могут похвастаться тем же. Они либо откровенно" +
                " неприятны, либо слишком мягкотелы и бесхарактерны как сам Живаго, который является простым свидетелем тех событий, " +
                "но не активным их участником. Он не вызвал у меня симпатии, но ее щедро получили все те, с кем сталкивала его судьба." +
                " Переплетение жизней героев кажется просто невероятным, что вызывает живой интерес к их участи, и в финале мы узнаем " +
                "итог жизни почти всех из них. Отдельно заслуживает внимание описании революций и их последствий! Тяжело было читать о " +
                "том, через что людям пришлось пройти, да и писать об этом не хочется. Но небольшой факт меня удивил - о царе всего одно" +
                " упоминание, и то времен первой мировой, когда он приезжал на смотр войск. В связи с революцией ни одного упоминание о " +
                "царской семье. Как итог могу сказать, что обязательно оставлю эту книгу на полке, чтобы сохранить в поколениях.", LocalDateTime.now(), 4.5);

        genreRepository.save(genre);
        genreRepository.save(genre2);

        authorRepository.save(authorModel);
        authorRepository.save(authorModel2);
        authorRepository.save(authorModel3);

        bookRepository.save(bookModel);
        bookRepository.save(bookModel2);
        bookRepository.save(bookModel3);

        reviewRepository.save(review);
    }
}
*/