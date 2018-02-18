package edu.msc.oidRegistry

/**
 *
 *
 * @Created - 12/28/17
 * @Author Marcelo Caldas mcq1@cdc.gov
 */
class OIDUtils {
    companion object {
        const val OID_REGEX: String = "([[0-9]+\\.])*"

        fun isValidOID(oid: String): Boolean {
            val regex = Regex(OID_REGEX)
            return regex.containsMatchIn(oid)
        }
    }

}