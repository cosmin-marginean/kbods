package org.kbods.rdf.plugins

import org.eclipse.rdf4j.model.Statement
import org.eclipse.rdf4j.model.vocabulary.OWL
import org.kbods.read.BodsStatement
import org.kbods.read.BodsStatementType
import org.kbods.read.JsonConst
import org.kbods.rdf.iri
import org.kbods.rdf.utils.CompaniesHouseUtils
import org.kbods.rdf.utils.add

/**
 * See https://www.data.gov.uk/dataset/5a33338a-e142-4f05-9458-ca7283f410b3/company-identifiers-uris for details
 */
class CompaniesHouseRefPlugin : BodsConvertPlugin {

    override val name: String = NAME
    override val statementType: BodsStatementType = BodsStatementType.ENTITY

    override fun generateStatements(bodsStatement: BodsStatement): List<Statement> {
        val statements = mutableListOf<Statement>()
        val subject = bodsStatement.iri()
        if (bodsStatement.jurisdictionCode == JsonConst.JURISDICTION_GB) {
            val companyNumber = bodsStatement.identifier(JsonConst.IDENTIFIER_SCHEME_COMPANIES_HOUSE)
            if (companyNumber != null) {
                statements.add(subject, OWL.SAMEAS, CompaniesHouseUtils.company(companyNumber))
            }
        }
        return statements
    }

    companion object {
        const val NAME = "uk-company-refs"
    }
}