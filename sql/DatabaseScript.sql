-- Movie Collection
USE master;

DROP DATABASE IF EXISTS MovieCollection;

CREATE DATABASE MovieCollection;

USE MovieCollection;

CREATE TABLE [Categories] (
    [Name] VARCHAR(255) PRIMARY KEY NOT NULL
);

CREATE TABLE [Movies] (
    [Id] INT IDENTITY(1, 1) PRIMARY KEY NOT NULL,
    [Title] NVARCHAR(255) NOT NULL,
    [FilePath] NVARCHAR(MAX) NOT NULL,
    [LastWatched] DATETIME DEFAULT GETDATE(),
    [PersonalRating] INT DEFAULT NULL,
    [IMDBRating] FLOAT DEFAULT NULL
);

CREATE TABLE [MovieCategoryRelation] (
    [Category] VARCHAR(255) FOREIGN KEY REFERENCES [Categories]([Name]),
    [Movie] INT FOREIGN KEY REFERENCES [Movies]([Id]),

    CONSTRAINT PK_RELATION PRIMARY KEY ([Category], [Movie])
);