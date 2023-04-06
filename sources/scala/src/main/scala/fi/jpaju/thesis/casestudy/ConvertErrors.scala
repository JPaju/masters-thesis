package fi.jpaju.thesis.casestudy

import org.postgresql.util.*
import zio.*

import java.sql.SQLException
import javax.sql.DataSource

def run(query: Any): IO[SQLException, Unit] = ???

class PostgresApikeyRepository(datasource: DataSource):
  def add(apikey: Apikey): IO[DuplicateApikey, Apikey] =
    // Prepare the insert query, implementation omitted for brevity
    val insertApikeyQuery: Query[Apikey] = ???

    // Run the query against a database
    val queryResult: IO[SQLException, Unit] = run(insertApikeyQuery)

    // Convert the SQLException to DuplicateApikey error or defect
    val uniqueViolationHandled: IO[DuplicateApikey, Unit] =
      queryResult
        .catchAll {
          case exc: SQLException if isUniqueViolation(exc) =>
            ZIO.fail(DuplicateApikey(apikey))

          case otherSqlExc: SQLException => ZIO.die(otherSqlExc)
        }

    // Return the original apikey after insert was successful
    uniqueViolationHandled.as(apikey)

  private def isUniqueViolation(exc: SQLException): Boolean =
    exc.getSQLState == PSQLState.UNIQUE_VIOLATION.getState

object PostgresApikeyRepository:
  val layer: ZLayer[DataSource, Nothing, ApikeyRepository] = ???
