package ch.addere.osv.impl.fields.affected;

import static ch.addere.osv.impl.fields.affected.DatabaseSpecificImpl.DATABASE_SPECIFIC_KEY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import ch.addere.osv.fields.affected.DatabaseSpecific;
import org.junit.jupiter.api.Test;

class DatabaseSpecificImplTest {

  private static final String DATABASE_SPECIFIC1 = "someDatabaseSpecificValues";
  private static final String DATABASE_SPECIFIC2 = "{\"CWE\":\"CWE-327\","
      + "\"CVSS\":{\"Score\":\"6.8\",\"Severity\":\"Medium\","
      + "\"Code\":\"CVSS:3.1/AV:N/AC:H/PR:N/UI:N/S:C/C:H/I:N/A:N\"}}";

  @Test
  void testJsonKey() {
    assertThat(DATABASE_SPECIFIC_KEY)
        .isEqualTo("database_specific");
  }

  @Test
  void testValidDatabaseSpecific() {
    DatabaseSpecific databaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    assertThat(databaseSpecific.value()).isEqualTo(DATABASE_SPECIFIC1);
  }

  @Test
  void testSameness() {
    DatabaseSpecific databaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    DatabaseSpecific otherDatabaseSpecific = databaseSpecific;
    assertThat(databaseSpecific).isEqualTo(otherDatabaseSpecific);
  }

  @Test
  void testEquality() {
    DatabaseSpecific databaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    DatabaseSpecific otherDatabaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    assertThat(databaseSpecific).isEqualTo(otherDatabaseSpecific);
  }

  @Test
  void testNonEquality() {
    DatabaseSpecific databaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    DatabaseSpecific otherDatabaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC2);
    assertThat(databaseSpecific).isNotEqualTo(otherDatabaseSpecific);
  }

  @Test
  void testHashCode() {
    DatabaseSpecific databaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    DatabaseSpecific otherDatabaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC1);
    assertThat(databaseSpecific.hashCode()).isEqualTo(otherDatabaseSpecific.hashCode());
  }

  @Test
  void testToString() {
    DatabaseSpecific databaseSpecific = DatabaseSpecificImpl.of(DATABASE_SPECIFIC2);
    assertThat(databaseSpecific.toString()).isEqualTo(
        DATABASE_SPECIFIC_KEY + ": " + DATABASE_SPECIFIC2);
  }
}