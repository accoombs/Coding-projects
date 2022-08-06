from neo4j import GraphDatabase
import spacy
import neuralcoref
import requests
import pdf2image
import pytesseract
import gzip
import PyPDF2
import pdfkit
import nltk
from pathlib import Path

pytesseract.pytesseract.tesseract_cmd = r"C:\Program Files\Tesseract-OCR\tesseract.exe"
path_wkhtmltopdf = r"C:\Program Files\wkhtmltopdf\bin\wkhtmltopdf.exe"
config = pdfkit.configuration(wkhtmltopdf=path_wkhtmltopdf)

class PDF_Pro:
    def finalize_text(self, url):
        def coref_resolution(text):
            nlp = spacy.load('en_core_web_lg')
            neuralcoref.add_to_pipe(nlp)
            doc = nlp(text)

            """Function that executes coreference resolution on a given text"""
            # fetches tokens with whitespaces from spacy document
            tok_list = list(token.text_with_ws for token in doc)
            for cluster in doc._.coref_clusters:
                # get tokens from representative cluster name
                cluster_main_words = set(cluster.main.text.split(' '))
                for coref in cluster:
                    if coref != cluster.main:  # if coreference element is not the representative element of that cluster
                        if coref.text != cluster.main.text and not (set(coref.text.split(' ')).intersection(cluster_main_words)):
                            # if coreference element text and representative element text are not equal and none of the coreference element words are in representative element. This was done to handle nested coreference scenarios
                            tok_list[coref.start] = cluster.main.text + \
                                doc[coref.end-1].whitespace_
                            for i in range(coref.start+1, coref.end):
                                tok_list[i] = ""

            return "".join(tok_list)
        pdf = requests.get(url)
        doc = pdf2image.convert_from_bytes(pdf.content)

        article = []
        for page_number, page_data in enumerate(doc):
            txt = pytesseract.image_to_string(page_data).encode("utf-8")
            # Sixth page are only references
            if page_number < 6:
                article.append(txt.decode("utf-8"))
        article_txt = " ".join(article)
        def clean_text(text):
            """Remove section titles and figure descriptions from text"""
            clean = "\n".join([row for row in text.split("\n") if (len(row.split(" "))) > 3 and not (row.startswith("(a)"))
                                and not row.startswith("Figure")])
            return coref_resolution(clean)

        text = article_txt
        ctext = clean_text(text)
        return nltk.tokenize.sent_tokenize(ctext)

        """
        urllib.request.urlretrieve(url, "filename.pdf")
        """

        """pdfkit.from_url('https://journals.plos.org/plosone/article?id=10.1371/journal.pone.0252248', 'slow.pdf', configuration=config, verbose = True)
        pdfFileObj = open('slow.pdf', 'rb')=
        pdfReader = PyPDF2.PdfFileReader(pdfFileObj)
        # Get the article text
        paragraphs = []
        for p in range(pdfReader.numPages):
            def get_sent(page):
                doc = page.extractText()
                def clean_text(text):
                    clean = ""
                    for row in text.split('\n'):    
                        if(len(row.split(" ")) > 3 and not (row.startswith("(a)"))
                                    and not row.startswith("Figure")):
                            clean+=row
                    #"\n".join([row for row in text.split('\n') if (len(row.split(" "))) > 3 and not (row.startswith("(a)"))
                    #               and not row.startswith("Figure")])
                    return coref_resolution(clean)
                return (clean_text(doc))
                return (nltk.tokenize.sent_tokenize(ctext))
            paragraphs.append(get_sent(pdfReader.getPage(p)))
        return paragraphs"""
"""
text = PDF_Pro().finalize_text()
for p in text:
    print(p)"""