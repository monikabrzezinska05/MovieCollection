-- Movie Collection
USE master;

DROP DATABASE IF EXISTS MovieCollection;

CREATE DATABASE MovieCollection;
USE MovieCollection;

USE MovieCollection;

CREATE TABLE [Categories] (
    [Name] VARCHAR(255) PRIMARY KEY NOT NULL
);

CREATE TABLE [Movies] (
    [Id] INT IDENTITY(1, 1) NOT NULL,
    [Title] NVARCHAR(255) NOT NULL,
    [FilePath] NVARCHAR(MAX) NOT NULL,
    [LastWatched] DATETIME,
);