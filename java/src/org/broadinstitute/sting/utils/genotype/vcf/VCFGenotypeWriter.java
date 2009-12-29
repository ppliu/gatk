package org.broadinstitute.sting.utils.genotype.vcf;

import org.broadinstitute.sting.utils.genotype.GenotypeWriter;

import java.util.Set;

/**
 * An extension of eth GenotypeWriter interface with support
 * for adding header lines.
 *
 * @author mhanna
 * @version 0.1
 */
public interface VCFGenotypeWriter extends GenotypeWriter {
    /**
     * initialize this VCF header
     *
     * @param sampleNames  the sample names
     * @param headerInfo  the optional header fields
     */
    public void writeHeader(Set<String> sampleNames, Set<VCFHeaderLine> headerInfo);

    /**
     * Add a given VCF record to the given output.
     * @param vcfRecord Record to add.
     */
    public void addRecord(VCFRecord vcfRecord);    

    /**
     * set the validation stringency
     * @param value   validation stringency value
     */
    public void setValidationStringency(VALIDATION_STRINGENCY value);
    
    public enum VALIDATION_STRINGENCY { STRICT, SILENT };
}
