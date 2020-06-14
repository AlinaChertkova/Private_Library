package com.example.personalLib.DB;

import com.example.personalLib.DB.Models.BookModel;
import com.example.personalLib.DB.Models.EditionModel;
import com.example.personalLib.DB.Models.PublishingModel;
import com.example.personalLib.DB.Repository.BookRepository;
import com.example.personalLib.DB.Repository.EditionRepository;
import com.example.personalLib.DB.Repository.PublishingRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class EditionTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EditionRepository editionRepository;
    @Autowired
    private PublishingRepository publishingRepository;

    @Test
    public void testSaveEdition() {

        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String bookDescription = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, bookDescription, coverLink, avgRating, 2);

        final BookModel savedBook = bookRepository.save(bookModel);

        final String name = "Alpina";
        final String pubDescription = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, pubDescription);

        final PublishingModel savedPub = publishingRepository.save(publishingModel);

        final EditionModel edition = new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", bookModel);
        final EditionModel savedEdition = editionRepository.save(edition);
        //Check book
        Assert.assertNotNull(savedEdition.getBook());
        Assert.assertEquals(savedEdition.getBook().getId(), savedBook.getId());

        //Check publishing
        Assert.assertNotNull(savedEdition.getPublishing());
        Assert.assertEquals(savedEdition.getPublishing().getId(), savedPub.getId());

        //Check other fields
        Assert.assertNotNull(savedEdition.getId());
        Assert.assertEquals(savedEdition.getSeries(), edition.getSeries());
        Assert.assertEquals(savedEdition.getYear(), edition.getYear());
        Assert.assertEquals(savedEdition.getCoverLink(), edition.getCoverLink());
    }

    @Test
    public void testDeleteEdition() {
        //Save book
        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String bookDescription = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, bookDescription, coverLink, avgRating, 2);
        final BookModel savedBook = bookRepository.save(bookModel);

        //Save publishing
        final String name = "Alpina";
        final String pubDescription = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, pubDescription);
        final PublishingModel savedPub = publishingRepository.save(publishingModel);

        //Save edition
        final EditionModel editoin = new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", bookModel);
        final EditionModel sevedEdition = editionRepository.save(editoin);

        //Check saved edition
        Optional<EditionModel> optSelectedValue = editionRepository.findById(sevedEdition.getId());
        Assert.assertTrue(optSelectedValue.isPresent());

        final EditionModel selectedValue = optSelectedValue.get();
        editionRepository.delete(selectedValue);
        //Check if edition id deleted
        Optional<EditionModel> optSelectedValue2 = editionRepository.findById(sevedEdition.getId());
        Assert.assertFalse(optSelectedValue2.isPresent());

        //Check saved book is still present
        Optional<BookModel> optSelectedValueBook = bookRepository.findById(savedBook.getId());
        Assert.assertTrue(optSelectedValueBook.isPresent());

        //Check publishing is still present
        Optional<PublishingModel> optSelectedValuePublishing = publishingRepository.findById(savedPub.getId());
        Assert.assertTrue(optSelectedValuePublishing.isPresent());
    }

    @Test
    public void testUpdateEdition() {
        //Save books
        final String ISBN = "978-5-389-02467-0";
        final String title = "June";
        final String bookDescription = "Drama";
        final String coverLink = "link";
        final double avgRating = 4.1;

        final BookModel bookModel = new BookModel(ISBN, title, bookDescription, coverLink, avgRating, 2);
        final BookModel savedBook = bookRepository.save(bookModel);

        final BookModel bookModel2 = new BookModel("new ISBN", "new title", "new description", "new link", 123.45, 2);
        final BookModel savedBook2 = bookRepository.save(bookModel2);

        //Save publishing
        final String name = "Alpina";
        final String pubDescription = "New pub";

        final PublishingModel publishingModel = new PublishingModel(name, pubDescription);
        final PublishingModel savedPub = publishingRepository.save(publishingModel);

        final String newDescription = "Издательство АСТ – одно из крупнейших издательств, занимающее" +
                " лидирующие позиции на российском книжном рынке, основанное в 1990 году. АСТ издает книги " +
                "практически всех жанров для самой широкой аудитории. Это интеллектуальная и развлекательная литература, " +
                "русская и зарубежная классика, учебники и учебные пособия, прикладные книги. Издательство выпускает более 40" +
                " млн. экземпляров книг в год и объединяет сильнейшую в стране редакционную команду.\n" +
                "\n" +
                "Основные принципы работы издательства – сохранять интеллектуальное мировое наследие; предоставлять" +
                " читателю максимально большой выбор литературы, соответствующей его личным и профессиональным интересам; " +
                "всегда быть в курсе мировых литературных трендов и делиться ими с читателем; быть социально-активными - " +
                "реализовывать рекламные кампании в поддержку чтения в России.\n" +
                "Издательство АСТ обладает крупнейшим авторским портфелем – более 5 000 имен.\n" +
                "\n" +
                "Ежемесячно из печати выходит более 500 новых книг. Среди авторов издательства – писатели с мировым именем " +
                "Стивен Кинг, Пауло Коэльо, Дэн Браун, Анна Гавальда, Донна Тартт, Нил Гейман, Сюзанн Коллинз, Харпер Ли, Пола " +
                "Хокинс, Энди Вейер, Джеймс Дэшнер, Джордж Мартин; а также популярные российские авторы: Борис Акунин, Дмитрий " +
                "Глуховский, Людмила Улицкая, Сергей Лукьяненко, Владимир Познер, Эдвард Радзинский, Гузель Яхина, Ольга " +
                "Узорова, Сергей Тармашев, Елена Михалкова и многие другие.\n" +
                "\n" +
                "В структуру издательства АСТ входят три основных редакционных Департамента. В состав каждого " +
                "Департамента входит несколько редакций-импринтов (брендов). Каждый импринт выпускает книги для своего " +
                "сегмента читателя:\n" +
                "\n" +
                "Художественная литература: «Neoclassic», «Corpus», «ЖАНРЫ», «АСТРЕЛЬ СПБ», «Редакция Елены Шубиной»," +
                " «Mainstream», «ИД Ленинград»; \n" +
                "Прикладная литература: «ВРЕМЕНА», «КЛАДЕЗЬ», «ПРАЙМ ЕВРО ЗНАК», «Lingua»; \n" +
                "Детская литература: «МАЛЫШ», «АСТРЕЛЬ», «Планета знаний», «АВАНТА»;\n" +
                "В 2005 году в издательскую группу «АСТ» вошло ООО «Аудиокнига», основное направление деятельности " +
                "которого — издание аудиоверсий произведений различных жанров российских и зарубежных авторов в прочтении " +
                "популярных актеров театра и кино.\n" +
                "В 2012 году издательство «АСТ» объединилось с издательством «ЭКСМО» и уже в 2013 году группа «ЭКСМО-АСТ» " +
                "заняла 45-е место в мировом рейтинге книгоиздателей.\n" +
                "Генеральный директор издательства АСТ – Гришков Павел Анатольевич\n";

        final PublishingModel publishingModel2 = new PublishingModel("new name", newDescription);
        final PublishingModel savedPub2 = publishingRepository.save(publishingModel2);

        //Save edition
        final EditionModel editoin = new EditionModel(publishingModel, 1999, "test_link", "Roza vetrov", bookModel);
        final EditionModel sevedEdition = editionRepository.save(editoin);
        //Check saved edition
        Optional<EditionModel> optSelectedValue = editionRepository.findById(sevedEdition.getId());
        Assert.assertTrue(optSelectedValue.isPresent());
        //Update
        final EditionModel selectedValue = optSelectedValue.get();

        selectedValue.setBook(bookModel2);
        selectedValue.setCoverLink("new cover");
        selectedValue.setPublishing(publishingModel2);
        selectedValue.setSeries("new AST series");
        selectedValue.setYear(2015);

        final EditionModel updated = editionRepository.save(editoin);

        Assert.assertEquals(selectedValue.getId(), updated.getId());
        Assert.assertEquals(selectedValue.getBook(), updated.getBook());
        Assert.assertEquals(selectedValue.getPublishing(), updated.getPublishing());
        Assert.assertEquals(selectedValue.getCoverLink(), updated.getCoverLink());
        Assert.assertEquals(selectedValue.getSeries(),  updated.getSeries());
        Assert.assertEquals(selectedValue.getYear(),  updated.getYear());

        //Check saved book is still present
        Optional<BookModel> optSelectedValueBook = bookRepository.findById(savedBook.getId());
        Assert.assertTrue(optSelectedValueBook.isPresent());

        //Check publishing is still present
        Optional<PublishingModel> optSelectedValuePublishing = publishingRepository.findById(savedPub.getId());
        Assert.assertTrue(optSelectedValuePublishing.isPresent());
    }
}
