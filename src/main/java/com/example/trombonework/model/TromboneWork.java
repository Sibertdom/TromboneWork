package com.example.trombonework.model;

public class TromboneWork {

    private Long id;                  // Унікальний ID
    private String title;             // Назва
    private Long composerId;
    private Composer composer;          // Композитор
    private String accompanimentType; // Тип супроводу (ф-но, оркестр тощо)
    private Integer difficultyLevel;  // Рівень складності (1-6)
    private String genre;             // Жанр (Концерт, Соната, П'єса)
    private String tempo;             // Темп (Allegro, Adagio)
    private String typeOfTrombone;    // Різновид тромбона (Тенор, Бас, Альт)

    // Додаткові поля:
    private String clef;              // Ключ (Басовий, Теноровий)
    private String keySignature;      // Тональність
    private String duration;          // Тривалість (наприклад "07:45")
    private String publisher;         // Видавництво
    private Integer yearComposed;     // Рік написання
    private String scoreUrl;          // Посилання на PDF ноти або відео
    private Boolean isFavorite;       // Прапорець обраного
    private String notes;             // Нотатки та примітки

    // Порожній конструктор
    public TromboneWork() {}

    // Приватний конструктор для Builder
    private TromboneWork(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.composer = builder.composer;
        this.accompanimentType = builder.accompanimentType;
        this.difficultyLevel = builder.difficultyLevel;
        this.genre = builder.genre;
        this.tempo = builder.tempo;
        this.typeOfTrombone = builder.typeOfTrombone;
        this.clef = builder.clef;
        this.keySignature = builder.keySignature;
        this.duration = builder.duration;
        this.publisher = builder.publisher;
        this.yearComposed = builder.yearComposed;
        this.scoreUrl = builder.scoreUrl;
        this.isFavorite = builder.isFavorite;
        this.notes = builder.notes;
    }

    public static Builder builder() {
        return new Builder();
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Composer getComposer() { return composer; }
    public void setComposer(Composer composer) { this.composer = composer; }

    public Long getComposerId() {
        return composerId;
    }

    public void setComposerId(Long composerId) {
        this.composerId = composerId;
    }

    public String getAccompanimentType() { return accompanimentType; }
    public void setAccompanimentType(String accompanimentType) { this.accompanimentType = accompanimentType; }

    public Integer getDifficultyLevel() { return difficultyLevel; }
    public void setDifficultyLevel(Integer difficultyLevel) { this.difficultyLevel = difficultyLevel; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getTempo() { return tempo; }
    public void setTempo(String tempo) { this.tempo = tempo; }

    public String getTypeOfTrombone() { return typeOfTrombone; }
    public void setTypeOfTrombone(String typeOfTrombone) { this.typeOfTrombone = typeOfTrombone; }

    public String getClef() { return clef; }
    public void setClef(String clef) { this.clef = clef; }

    public String getKeySignature() { return keySignature; }
    public void setKeySignature(String keySignature) { this.keySignature = keySignature; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public Integer getYearComposed() { return yearComposed; }
    public void setYearComposed(Integer yearComposed) { this.yearComposed = yearComposed; }

    public String getScoreUrl() { return scoreUrl; }
    public void setScoreUrl(String scoreUrl) { this.scoreUrl = scoreUrl; }

    public Boolean getIsFavorite() { return isFavorite; }
    public void setIsFavorite(Boolean isFavorite) { this.isFavorite = isFavorite; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Клас Builder
    public static class Builder {
        private Long id;
        private String title;
        private Long composerId;
        private Composer composer;
        private String accompanimentType;
        private Integer difficultyLevel;
        private String genre;
        private String tempo;
        private String typeOfTrombone;
        private String clef;
        private String keySignature;
        private String duration;
        private String publisher;
        private Integer yearComposed;
        private String scoreUrl;
        private Boolean isFavorite;
        private String notes;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder title(String title) { this.title = title; return this; }
        public Builder composerId(Long composerId) {
            this.composerId = composerId;
            return this;
        }
        public Builder composer(Composer composer) { this.composer = composer; return this; }
        public Builder accompanimentType(String accompanimentType) { this.accompanimentType = accompanimentType; return this; }
        public Builder difficultyLevel(Integer difficultyLevel) { this.difficultyLevel = difficultyLevel; return this; }
        public Builder genre(String genre) { this.genre = genre; return this; }
        public Builder tempo(String tempo) { this.tempo = tempo; return this; }
        public Builder typeOfTrombone(String typeOfTrombone) { this.typeOfTrombone = typeOfTrombone; return this; }
        public Builder clef(String clef) { this.clef = clef; return this; }
        public Builder keySignature(String keySignature) { this.keySignature = keySignature; return this; }
        public Builder duration(String duration) { this.duration = duration; return this; }
        public Builder publisher(String publisher) { this.publisher = publisher; return this; }
        public Builder yearComposed(Integer yearComposed) { this.yearComposed = yearComposed; return this; }
        public Builder scoreUrl(String scoreUrl) { this.scoreUrl = scoreUrl; return this; }
        public Builder isFavorite(Boolean isFavorite) { this.isFavorite = isFavorite; return this; }
        public Builder notes(String notes) { this.notes = notes; return this; }

        public TromboneWork build() {
            return new TromboneWork(this);
        }
    }
}