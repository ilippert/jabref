package org.jabref.model.search.event;

import java.util.List;

import org.jabref.model.entry.BibEntry;

public record IndexRemovedEvent(List<BibEntry> removedEntries) {
}
