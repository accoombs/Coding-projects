import nltk
from coreferences import PDF_Pro
from nltk.corpus import wordnet
from nltk.corpus import stopwords
import pandas
from web_scraping import web_scraping
import csv
import os

class NLP:
    def __init__(self):
        self.stop_words = set(stopwords.words('english'))
        self.si = wordnet.synset('color.n.1')
        self.sm = wordnet.synset('smell.n.1')
        self.he = wordnet.synset('hearing.n.1')
        self.ta = wordnet.synset('taste.n.1')
        self.to = wordnet.synset('touch.n.1')
        TAKE_OUT = ['hrs', 'end', '+', 'https', 'back']
        for w in TAKE_OUT:
            self.stop_words.add(w)
    def get_entities(self):
        web_scraping().scrape("https://isomerdesign.com/PiHKAL/explore.php?domain=pk")
        #putting links into a CSV file
        """with open('notebook_links.csv', mode = 'r') as f:
            content = csv.DictReader(f)"""
        df = pandas.read_csv('notebook_links.csv')
        urls = [link for link in df['link']]
        association = []
        words = {}
        for url in urls:
            print(url, " LINK")
            sentences = PDF_Pro().finalize_text(url)
            for sentence in sentences:
                token = nltk.word_tokenize(sentence)
                tagged = nltk.pos_tag(token)
                entities = nltk.chunk.ne_chunk(tagged)
                for e in entities:
                    if ('NN' in e or 'JJ' in e )and e[0] not in self.stop_words:
                        words[e] = 1+words.get(e,0)
        """for w in words:
            print(w, words[w])""" 
        def sense_association(w, reps):
            senses = {'word':w[0],'reps':reps, 'senses':[]}
            try:
                if w[1] == 'NN':
                    w1 = wordnet.synset(w[0]+'.n.1')
                elif w[1] == 'JJ':
                    w1 = wordnet.synset(w[0]+'.a.1')
                threshold = .25
                if((w1.wup_similarity(self.si)) > threshold):
                    senses['senses'].append(1)
                else: 
                    senses['senses'].append(0)
                if((w1.wup_similarity(self.sm)) > threshold):
                    senses['senses'].append(1)
                else: 
                    senses['senses'].append(0)
                if((w1.wup_similarity(self.he)) > threshold):
                    senses['senses'].append(1)
                else: 
                    senses['senses'].append(0)
                if((w1.wup_similarity(self.to)) > threshold):
                    senses['senses'].append(1)
                else: 
                    senses['senses'].append(0)
                if((w1.wup_similarity(self.ta)) > threshold):
                    senses['senses'].append(1)
                else: 
                    senses['senses'].append(0)
                return senses
            except:
                return
        for w in words:
            word = sense_association(w, words[w])
            if word:
                association.append(word)
        #return sorted(association, key=lambda d: d['reps'], reverse=True)
        filename = 'Mescaline.csv'
        fields = ['WORD', 'REPS', 'SIGHT', 'SMELL', 'HEARING', 'TOUCH', 'TASTE', 'TITLE']
        with open(filename, 'w', newline='') as csvfile:
            csvwriter = csv.writer(csvfile)
            csvwriter.writerow(fields)
            for d in association:
                csvwriter.writerow([d['word'], d['reps'],d['senses'][0], d['senses'][1], d['senses'][2], d['senses'][3], d['senses'][4], 'Mescaline'])
        os.replace(r"C:\Users\ikixs\Mescaline.csv", r"C:\Users\ikixs\.Neo4jDesktop\relate-data\dbmss\dbms-46f92842-e79b-4145-9b22-ed705a6f1292\import\Mescaline.csv")

"""entities = NLP().get_entities()"""
"""print(len(entities))
for e in entities:
    print(e)"""

#dict(sorted(obj.get_entities().items(), key=lambda item: item[1], reverse=True)