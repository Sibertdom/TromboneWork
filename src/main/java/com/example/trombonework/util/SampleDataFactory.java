package com.example.trombonework.util;

import com.example.trombonework.model.Composer;
import com.example.trombonework.model.TromboneWork;

import java.util.ArrayList;
import java.util.List;

public class SampleDataFactory {

    public static List<Composer> getInitialComposers() {
        List<Composer> composers = new ArrayList<>();

        composers.add(Composer.builder()
                .id(1L)
                .firstName("Ferdinand")
                .lastName("David")
                .nationality("Німеччина")
                .birthYear(1810)
                .deathYear(1873)
                .era("Романтизм")
                .biography("Німецький скрипаль-віртуоз, композитор та педагог.")
                .build());

        composers.add(Composer.builder()
                .id(2L)
                .firstName("Ernst")
                .lastName("Sachse")
                .nationality("Німеччина")
                .birthYear(1810)
                .deathYear(1849)
                .era("Романтизм")
                .biography("Німецький трубач і композитор XIX століття.")
                .build());

        composers.add(Composer.builder()
                .id(3L)
                .firstName("Georg Christoph")
                .lastName("Wagenseil")
                .nationality("Австрія")
                .birthYear(1715)
                .deathYear(1777)
                .era("Класицизм")
                .biography("Австрійський композитор та органіст епохи класицизму.")
                .build());

        return composers;
    }

    public static List<TromboneWork> getInitialWorks() {
        List<TromboneWork> works = new ArrayList<>();
        List<Composer> composers = getInitialComposers();

        Composer david = composers.get(0);
        Composer sachse = composers.get(1);
        Composer wagenseil = composers.get(2);

        TromboneWork davidConcerto = TromboneWork.builder()
                .id(1L)
                .title("Concertino in E-flat major, Op. 4")
                .composer(david)
                .composerId(david.getId())
                .typeOfTrombone("Тенор")
                .accompanimentType("Piano / Orchestra")
                .difficultyLevel(5)
                .genre("Концерт")
                .tempo("Allegro maestoso")
                .clef("Tenor / Bass")
                .keySignature("E-flat Major")
                .duration("15:00")
                .publisher("International Music Company (IMC)")
                .yearComposed(1837)
                .isFavorite(true)
                .notes("Перший класичний концерт для тромбона романтичної епохи.")
                .build();

        TromboneWork sachseBassConcerto = TromboneWork.builder()
                .id(2L)
                .title("Concertino in F major")
                .composer(sachse)
                .composerId(sachse.getId())
                .typeOfTrombone("Бас")
                .accompanimentType("Piano / Orchestra")
                .difficultyLevel(4)
                .genre("Концерт")
                .tempo("Allegro / Adagio / Theme & Variations")
                .clef("Bass")
                .keySignature("F Major")
                .duration("12:00")
                .publisher("Editions Marc Reift")
                .yearComposed(1880)
                .isFavorite(false)
                .notes("Один із найпопулярніших сольних творів для басового тромбона романтичного періоду.")
                .build();

        TromboneWork wagenseilConcerto = TromboneWork.builder()
                .id(3L)
                .title("Concerto for Alto Trombone in E-flat major")
                .composer(wagenseil)
                .composerId(wagenseil.getId())
                .typeOfTrombone("Альт")
                .accompanimentType("Piano / Orchestra")
                .difficultyLevel(4)
                .genre("Концерт")
                .tempo("Adagio / Allegro assai")
                .clef("Alto")
                .keySignature("E-flat Major")
                .duration("10:00")
                .publisher("Willy Müller Süddeutscher Musikverlag")
                .yearComposed(1755)
                .isFavorite(true)
                .notes("Перлинка класицизму, один із найдавніших збережених сольних концертів для альтового тромбона.")
                .build();

        TromboneWork sachseTenorConcerto = TromboneWork.builder()
                .id(4L)
                .title("Concertino in B-flat major")
                .composer(sachse)
                .composerId(sachse.getId())
                .typeOfTrombone("Тенор")
                .accompanimentType("Piano / Orchestra")
                .difficultyLevel(4)
                .genre("Концерт")
                .tempo("Allegro / Adagio / Theme & Variations")
                .clef("Tenor / Bass")
                .keySignature("B-flat Major")
                .duration("12:00")
                .publisher("International Music Company (IMC)")
                .yearComposed(1880)
                .isFavorite(false)
                .notes("Популярне перекладення Концертіно Заксе для тенорового тромбона.")
                .build();

        works.add(davidConcerto);
        works.add(sachseBassConcerto);
        works.add(wagenseilConcerto);
        works.add(sachseTenorConcerto);

        return works;
    }
}