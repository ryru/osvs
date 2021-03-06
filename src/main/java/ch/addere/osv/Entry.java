package ch.addere.osv;

import static java.util.stream.Collectors.joining;

import ch.addere.osv.property.AffectedValues;
import ch.addere.osv.property.AliasesValue;
import ch.addere.osv.property.DetailsValue;
import ch.addere.osv.property.IdValue;
import ch.addere.osv.property.ModifiedValue;
import ch.addere.osv.property.PublishedValue;
import ch.addere.osv.property.ReferencesValues;
import ch.addere.osv.property.RelatedValue;
import ch.addere.osv.property.SummaryValue;
import ch.addere.osv.property.WithdrawnValue;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Entry is an open source vulnerability.
 */
public final class Entry {

  private final IdValue id;
  private final ModifiedValue modified;
  private AliasesValue aliases;
  private RelatedValue related;
  private PublishedValue published;
  private WithdrawnValue withdrawn;
  private SummaryValue summary;
  private DetailsValue detailsValue;
  private List<AffectedValues> affected;
  private List<ReferencesValues> references;

  private Entry(IdValue id, ModifiedValue modified) {
    this.id = id;
    this.modified = modified;
  }

  /**
   * Entity builder creates new entities.
   * <p>A minimal entity requires an ID and a modified attribute.</p>
   *
   * @param id       unique ID
   * @param modified last modification date
   * @return valid entity
   */
  public static EntryBuilder builder(IdValue id, ModifiedValue modified) {
    return new EntryBuilder(id, modified);
  }

  /**
   * Get the ID.
   *
   * @return the ID of this Entry
   */
  public IdValue id() {
    return id;
  }

  /**
   * Get the ModifiedValue.
   *
   * @return the ModifiedValues of this Entry
   */
  public ModifiedValue modified() {
    return modified;
  }

  /**
   * Get a IdAggregate of aliases IDs.
   *
   * @return IdAggregate if present, otherwise empty optional
   */
  public Optional<AliasesValue> aliases() {
    return Optional.ofNullable(aliases);
  }

  /**
   * Get a IdAggregate of related IDs.
   *
   * @return IdAggregate if present, otherwise empty optional
   */
  public Optional<RelatedValue> related() {
    return Optional.ofNullable(related);
  }

  /**
   * Get a PublishedValue.
   *
   * @return PublishedValues if present, otherwise empty optional
   */
  public Optional<PublishedValue> published() {
    return Optional.ofNullable(published);
  }

  /**
   * Get a WithdrawnValue.
   *
   * @return WithdrawnValues if present, otherwise empty optional
   */
  public Optional<WithdrawnValue> withdrawn() {
    return Optional.ofNullable(withdrawn);
  }

  /**
   * Get a SummaryValue.
   *
   * @return SummaryValues if present, otherwise empty optional
   */
  public Optional<SummaryValue> summary() {
    return Optional.ofNullable(summary);
  }

  /**
   * Get a DetailsValue.
   *
   * @return DetailsValues if present, otherwise empty optional
   */
  public Optional<DetailsValue> details() {
    return Optional.ofNullable(detailsValue);
  }

  /**
   * Get a copied list of AffectedValues.
   *
   * @return copied list if AffectedValues are present, otherwise empty list
   */
  public List<AffectedValues> affected() {
    if (affected != null) {
      return new LinkedList<>(affected);
    } else {
      return List.of();
    }
  }

  /**
   * Get a copied list of ReferencesValues.
   *
   * @return copied list if ReferencesValues are present, otherwise empty list
   */
  public List<ReferencesValues> references() {
    if (references != null) {
      return new LinkedList<>(references);
    } else {
      return List.of();
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Entry entry = (Entry) o;
    return id.equals(entry.id) && modified.equals(entry.modified) && Objects.equals(aliases,
        entry.aliases) && Objects.equals(related, entry.related)
        && Objects.equals(published, entry.published) && Objects.equals(withdrawn,
        entry.withdrawn) && Objects.equals(summary, entry.summary)
        && Objects.equals(detailsValue, entry.detailsValue) && Objects.equals(affected,
        entry.affected) && Objects.equals(references, entry.references);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, modified, aliases, related, published, withdrawn, summary, detailsValue,
        affected, references);
  }

  @Override
  public String toString() {
    return Stream.of(
            id,
            modified,
            aliases,
            related,
            published,
            withdrawn,
            summary,
            detailsValue,
            affected,
            references
        )
        .filter(Objects::nonNull)
        .map(Objects::toString)
        .filter(s -> !s.isEmpty())
        .collect(joining(", "));
  }

  /**
   * Builder for Entries.
   */
  public static final class EntryBuilder {

    private final IdValue id;
    private final ModifiedValue modified;
    private AliasesValue aliases = null;
    private RelatedValue related = null;
    private PublishedValue published = null;
    private WithdrawnValue withdrawn = null;
    private SummaryValue summary = null;
    private DetailsValue detailsValue = null;
    private List<AffectedValues> affected = null;
    private List<ReferencesValues> references = null;

    /**
     * Entry builder.
     *
     * @param id       ID of this entry
     * @param modified Modified of this entry
     */
    public EntryBuilder(IdValue id, ModifiedValue modified) {
      Objects.requireNonNull(id, "argument id must not be null");
      Objects.requireNonNull(modified, "argument modified must not be null");
      this.id = id;
      this.modified = modified;
    }

    /**
     * Add Aliases IdAggregate to property.
     *
     * @param aliases Aliases IdAggregate to add
     * @return valid EntryBuilder
     */
    public EntryBuilder aliases(AliasesValue aliases) {
      this.aliases = aliases;
      return this;
    }

    /**
     * Add Related IdAggregate to property.
     *
     * @param related IdAggregate to add
     * @return valid EntryBuilder
     */
    public EntryBuilder related(RelatedValue related) {
      this.related = related;
      return this;
    }

    /**
     * Add PublishedValue to property.
     *
     * @param published PublishedValue to add
     * @return valid EntryBuilder
     */
    public EntryBuilder published(PublishedValue published) {
      this.published = published;
      return this;
    }

    /**
     * Add WithdrawnValue to property.
     *
     * @param withdrawn WithdrawnValue to add
     * @return valid EntryBuilder
     */
    public EntryBuilder withdrawn(WithdrawnValue withdrawn) {
      this.withdrawn = withdrawn;
      return this;
    }

    /**
     * Add SummaryValue to property.
     *
     * @param summary SummaryValue to add
     * @return valid EntryBuilder
     */
    public EntryBuilder summary(SummaryValue summary) {
      this.summary = summary;
      return this;
    }

    /**
     * Add DetailsValue to property.
     *
     * @param detailsValue DetailsValue to add
     * @return valid EntryBuilder
     */
    public EntryBuilder details(DetailsValue detailsValue) {
      this.detailsValue = detailsValue;
      return this;
    }

    /**
     * Add AffectedValues to property.
     *
     * @param affected AffectedValues to add
     * @return valid EntryBuilder
     */
    public EntryBuilder affected(AffectedValues... affected) {
      this.affected = List.of(affected);
      return this;
    }

    /**
     * Add ReferencesValues to property.
     *
     * @param references ReferencesValues to add
     * @return valid EntryBuilder
     */
    public EntryBuilder references(ReferencesValues... references) {
      this.references = List.of(references);
      return this;
    }

    /**
     * Build a concrete entry.
     *
     * @return valid entry
     */
    public Entry build() {
      Entry entry = new Entry(id, modified);
      entry.aliases = aliases;
      entry.related = related;
      entry.published = published;
      entry.withdrawn = withdrawn;
      entry.summary = summary;
      entry.detailsValue = detailsValue;
      entry.affected = affected;
      entry.references = references;
      return entry;
    }
  }
}
