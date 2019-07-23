package de.jcup.sqleditor;

import java.util.Iterator;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.query.QueryStatement;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParseErrorInfo;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParserException;
import org.eclipse.datatools.sqltools.parsers.sql.SQLParserInternalException;
import org.eclipse.datatools.sqltools.parsers.sql.query.SQLQueryParseResult;
import org.eclipse.datatools.sqltools.parsers.sql.query.SQLQueryParserManager;
import org.eclipse.datatools.sqltools.parsers.sql.query.SQLQueryParserManagerProvider;

public class TestSQLParser {

    public static void main(String[] args) throws Exception {
        try {

            // Create an instance the Parser Manager

            // SQLQueryParserManagerProvider.getInstance().getParserManager

            // returns the best compliant SQLQueryParserManager

            // supporting the SQL dialect of the database described by the given

            // database product information. In the code below null is passed for both the
            // database and version

            // in which case a generic parser is returned

            SQLQueryParserManager parserManager = SQLQueryParserManagerProvider

                    .getInstance().getParserManager(null, null);

            // Sample query

            String sql = "SELECT * FROM TABLE1";

            // Parse

            SQLQueryParseResult parseResult = parserManager.parseQuery(sql);

            // Get the Query Model object from the result

            QueryStatement resultObject = parseResult.getQueryStatement();

            // Get the SQL text

            String parsedSQL = resultObject.getSQL();

            System.out.println(parsedSQL);

        } catch (SQLParserException spe) {

            // handle the syntax error

            System.out.println(spe.getMessage());

            List syntacticErrors = spe.getErrorInfoList();

            Iterator itr = syntacticErrors.iterator();

            while (itr.hasNext()) {

                SQLParseErrorInfo errorInfo = (SQLParseErrorInfo) itr.next();

                // Example usage of the SQLParseErrorInfo object

                // the error message

                String errorMessage = errorInfo.getParserErrorMessage();

                // the line numbers of error

                int errorLine = errorInfo.getLineNumberStart();

                int errorColumn = errorInfo.getColumnNumberStart();

            }

        } catch (SQLParserInternalException spie) {

            // handle the exception

            System.out.println(spie.getMessage());

        }
    }
}
