open module org.jabref {
    // Swing
    requires java.desktop;

    // SQL
    requires java.sql;
    requires java.sql.rowset;

    // region JavaFX
    requires javafx.base;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.web;
    requires javafx.fxml;

    requires afterburner.fx;
    provides com.airhacks.afterburner.views.ResourceLocator
            with org.jabref.gui.util.JabRefResourceLocator;

    requires com.dlsc.gemsfx;
    uses com.dlsc.gemsfx.TagsField;

    requires com.tobiasdiez.easybind;

    requires de.saxsys.mvvmfx;
    requires de.saxsys.mvvmfx.validation;

    requires org.controlsfx.controls;
    requires org.fxmisc.flowless;
    requires org.fxmisc.richtext;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.materialdesign2;
    uses org.kordamp.ikonli.IkonHandler;
    uses org.kordamp.ikonli.IkonProvider;

    provides org.kordamp.ikonli.IkonHandler
            with org.jabref.gui.icon.JabRefIkonHandler;
    provides org.kordamp.ikonli.IkonProvider
            with org.jabref.gui.icon.JabrefIconProvider;

    requires reactfx;
    // endregion

    // region: Logging
    requires org.slf4j;
    requires jul.to.slf4j;
    requires org.apache.logging.log4j.to.slf4j;
    requires org.tinylog.api;
    requires org.tinylog.api.slf4j;
    requires org.tinylog.impl;
    // endregion

    provides org.tinylog.writers.Writer
    with org.jabref.gui.logging.GuiWriter;

    // Preferences and XML
    requires java.prefs;
    requires com.fasterxml.aalto;

    // YAML
    requires org.yaml.snakeyaml;

    // region: Annotations (@PostConstruct)
    requires jakarta.annotation;
    requires jakarta.inject;
    // endregion

    // region: http server and client exchange
    requires java.net.http;
    requires jakarta.ws.rs;
    requires org.glassfish.grizzly;
    // endregion

    // region: data mapping
    requires jakarta.xml.bind;
    requires jdk.xml.dom;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.datatype.jsr310;
    // needs to be loaded here as it's otherwise not found at runtime
    requires org.glassfish.jaxb.runtime;
    // endregion

    // dependency injection using HK2
    requires org.glassfish.hk2.api;

    // region: http clients
    requires unirest.java.core;
    requires unirest.modules.gson;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires org.jsoup;
    // endregion

    // region: SQL databases
    requires ojdbc10;
    requires org.postgresql.jdbc;
    requires org.mariadb.jdbc;
    uses org.mariadb.jdbc.credential.CredentialPlugin;
    // endregion

    // region: Apache Commons and other (similar) helper libraries
    requires com.google.common;
    requires io.github.javadiffutils;
    requires java.string.similarity;
    requires org.apache.commons.cli;
    requires org.apache.commons.csv;
    requires org.apache.commons.io;
    requires org.apache.commons.lang3;
    requires org.apache.commons.text;
    requires org.apache.commons.logging;
    // endregion

    // region: latex2unicode
    requires com.github.tomtung.latex2unicode;
    requires fastparse;
    requires scala.library;
    // endregion

    requires jbibtex;
    requires citeproc.java;

    requires snuggletex.core;

    requires org.apache.pdfbox;
    requires org.apache.xmpbox;
    requires com.ibm.icu;

    requires flexmark;
    requires flexmark.html2md.converter;
    requires flexmark.util.ast;
    requires flexmark.util.data;

    requires com.h2database.mvstore;

    requires java.keyring;
    requires org.freedesktop.dbus;

    requires org.jooq.jool;

    // region: fulltext search
    /**
     * In case the version is updated, please also adapt {@link org.jabref.model.search.SearchFieldConstants#VERSION} to the newly used version.
     */
    uses org.apache.lucene.codecs.lucene99.Lucene99Codec;
    requires org.apache.lucene.core;
    requires org.apache.lucene.queryparser;
    requires org.apache.lucene.highlighter;
    requires org.apache.lucene.analysis.common;
    // endregion

    requires net.harawata.appdirs;
    requires com.sun.jna;
    requires com.sun.jna.platform;

    requires org.eclipse.jgit;
    uses org.eclipse.jgit.transport.SshSessionFactory;
    uses org.eclipse.jgit.lib.GpgSigner;

    requires transitive org.jspecify;

    // region: other libraries (alphabetically)
    requires dd.plist;
    requires mslinks;
    requires org.antlr.antlr4.runtime;
    requires org.libreoffice.uno;
    // endregion
}
