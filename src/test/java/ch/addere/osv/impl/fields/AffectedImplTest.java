package ch.addere.osv.impl.fields;

import static ch.addere.osv.fields.affected.Ranges.RANGES_KEY;
import static ch.addere.osv.fields.affected.ranges.Event.EVENTS_KEY;
import static ch.addere.osv.impl.fields.AffectedImpl.AFFECTED_KEY;
import static ch.addere.osv.impl.fields.affected.DatabaseSpecificImpl.DATABASE_SPECIFIC_KEY;
import static ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl.ECOSYSTEM_SPECIFIC_KEY;
import static ch.addere.osv.impl.fields.affected.PackageImpl.PACKAGE_KEY;
import static ch.addere.osv.impl.fields.affected.VersionsImpl.VERSIONS_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl.ECOSYSTEM_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.NameImpl.NAME_KEY;
import static ch.addere.osv.impl.fields.affected.pckg.PurlImpl.PURL_KEY;
import static ch.addere.osv.impl.fields.affected.ranges.RangeTypeImpl.TYPE_KEY;
import static java.lang.String.join;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import ch.addere.osv.fields.Affected;
import ch.addere.osv.fields.affected.DatabaseSpecific;
import ch.addere.osv.fields.affected.EcosystemSpecific;
import ch.addere.osv.fields.affected.Package;
import ch.addere.osv.fields.affected.Ranges;
import ch.addere.osv.fields.affected.Versions;
import ch.addere.osv.impl.fields.AffectedImpl.AffectedBuilder;
import ch.addere.osv.impl.fields.affected.DatabaseSpecificImpl;
import ch.addere.osv.impl.fields.affected.EcosystemSpecificImpl;
import ch.addere.osv.impl.fields.affected.PackageImpl;
import ch.addere.osv.impl.fields.affected.VersionsImpl;
import ch.addere.osv.impl.fields.affected.pckg.EcosystemImpl;
import ch.addere.osv.impl.fields.affected.pckg.NameImpl;
import ch.addere.osv.impl.fields.affected.pckg.PurlImpl;
import ch.addere.osv.impl.fields.affected.ranges.TypeSemVerImpl;
import ch.addere.osv.impl.fields.affected.ranges.events.EventSpecifierImpl;
import ch.addere.osv.impl.fields.affected.ranges.events.SemVerEvent;
import org.junit.jupiter.api.Test;

class AffectedImplTest {

  private static final String VERSION = "aVersion";

  @Test
  void testJsonKey() {
    assertThat(AFFECTED_KEY).isEqualTo("affected");
  }

  @Test
  void testOfPackageNull() {
    assertThatThrownBy(() -> new AffectedBuilder(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument package must not be null");
  }

  @Test
  void testOfRangesNull() {
    assertThatThrownBy(() -> new AffectedBuilder(pckg()).ranges((Ranges[]) null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ranges must not be null");
  }

  @Test
  void testOfVersionsNull() {
    assertThatThrownBy(() -> new AffectedBuilder(pckg()).versions(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument versions must not be null");
  }

  @Test
  void testOfEcosystemSpecificNull() {
    assertThatThrownBy(() -> new AffectedBuilder(pckg()).ecosystemSpecific(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument ecosystem specific must not be null");
  }

  @Test
  void testOfDatabaseSpecificNull() {
    assertThatThrownBy(() -> new AffectedBuilder(pckg()).databaseSpecific(null))
        .isInstanceOf(NullPointerException.class)
        .hasMessageContaining("argument database specific must not be null");
  }

  @Test
  void testValidAffected() {
    Affected affected = new AffectedBuilder(pckg())
        .ranges(ranges())
        .versions(versions())
        .databaseSpecific(databaseSpecific())
        .ecosystemSpecific(ecosystemSpecific())
        .build();
    assertThat(affected).satisfies(aff -> {
      assertThat(aff.pckg()).isEqualTo(pckg());
      assertThat(aff.ranges().toArray()).containsExactly(ranges());
      assertThat(aff.versions()).contains(versions());
      assertThat(aff.databaseSpecific()).contains(databaseSpecific());
      assertThat(aff.ecosystemSpecific()).contains(ecosystemSpecific());
    });
  }

  @Test
  void testValidAffectedWithVersion() {
    Affected affected = new AffectedBuilder(pckg())
        .versions(versions())
        .build();
    assertThat(affected).satisfies(aff -> {
      assertThat(aff.pckg()).isEqualTo(pckg());
      assertThat(aff.versions()).contains(versions());
    });
  }

  @Test
  void testValidAffectedWithSemVerRange() {
    Affected affected = new AffectedBuilder(pckg())
        .ranges(ranges())
        .build();
    assertThat(affected).satisfies(aff -> {
      assertThat(aff.pckg()).isEqualTo(pckg());
      assertThat(aff.ranges().toArray()).containsExactly(ranges());
    });
  }

  @Test
  void testInvalidAffectedWithoutVersionOrSemVerRange() {
    assertThatThrownBy(() -> new AffectedBuilder(pckg()).build())
        .isInstanceOf(IllegalStateException.class)
        .hasMessageContaining("no versions or no range of type semantic version");
  }

  @Test
  void testEquality() {
    Affected affected = new AffectedBuilder(pckg()).versions(VersionsImpl.of(VERSION)).build();
    Affected otherAffected = new AffectedBuilder(pckg()).versions(VersionsImpl.of(VERSION)).build();
    assertThat(affected).isEqualTo(otherAffected);
  }

  @Test
  void testNonEquality() {
    Affected affected = new AffectedBuilder(pckg()).versions(VersionsImpl.of(VERSION)).build();
    Affected otherAffected = new AffectedBuilder(pckg()).versions(VersionsImpl.of("anotherVersion"))
        .build();
    assertThat(affected).isNotEqualTo(otherAffected);
  }

  @Test
  void testHashCode() {
    Affected affected = new AffectedBuilder(pckg()).ranges(ranges())
        .versions(VersionsImpl.of(VERSION))
        .build();
    Affected otherAffected = new AffectedBuilder(pckg()).ranges(ranges())
        .versions(VersionsImpl.of(VERSION)).build();
    assertThat(affected).hasSameHashCodeAs(otherAffected);
  }

  @Test
  void testToString() {
    Affected affected = new AffectedBuilder(pckg())
        .ranges(ranges())
        .versions(VersionsImpl.of(VERSION))
        .ecosystemSpecific(EcosystemSpecificImpl.of("{\"some\":\"ecosystem specific properties\"}"))
        .databaseSpecific(DatabaseSpecificImpl.of("{\"some\":\"database specific properties\"}"))
        .build();
    assertThat(affected).hasToString(AFFECTED_KEY + ": "
        + join(", ",
        packageToString(),
        rangesToString(),
        versionsToString(),
        ecosystemSpecificToString(),
        databaseSpecificToString()));
  }

  private static Package pckg() {
    return PackageImpl.of(
        EcosystemImpl.GO,
        NameImpl.of("aName"),
        PurlImpl.of("pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie"));
  }

  private static Ranges ranges() {
    return TypeSemVerImpl.of(SemVerEvent.of(EventSpecifierImpl.INTRODUCED, "1.0.0"));
  }

  private static Versions versions() {
    return VersionsImpl.of("1.0.0");
  }

  private static DatabaseSpecific databaseSpecific() {
    return DatabaseSpecificImpl.of("a database specific value");
  }

  private static EcosystemSpecific ecosystemSpecific() {
    return EcosystemSpecificImpl.of("an ecosystem specific value");
  }

  private static String packageToString() {
    return PACKAGE_KEY + ": "
        + join(", ",
        packageEcosystemToString(),
        packageNameToString(),
        packagePurlToString());
  }

  private static String packageEcosystemToString() {
    return ECOSYSTEM_KEY + ": " + "Go";
  }

  private static String packageNameToString() {
    return NAME_KEY + ": " + "aName";
  }

  private static String packagePurlToString() {
    return PURL_KEY + ": " + "pkg:deb/debian/curl@7.50.3-1?arch=i386&distro=jessie";
  }

  private static String rangesToString() {
    return RANGES_KEY + ": " + join(", ",
        rangesTypeToString(),
        rangesEventsToString());
  }

  private static String rangesTypeToString() {
    return TYPE_KEY + ": " + "SEMVER";
  }

  private static String rangesEventsToString() {
    return EVENTS_KEY + ": " + join(", ", "introduced", "1.0.0");
  }

  private static String versionsToString() {
    return VERSIONS_KEY + ": " + VERSION;
  }

  private static String ecosystemSpecificToString() {
    return ECOSYSTEM_SPECIFIC_KEY + ": "
        + "{\"some\":\"ecosystem specific properties\"}";
  }

  private static String databaseSpecificToString() {
    return DATABASE_SPECIFIC_KEY + ": "
        + "{\"some\":\"database specific properties\"}";
  }
}
