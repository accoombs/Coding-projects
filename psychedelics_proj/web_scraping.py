from bs4 import BeautifulSoup
import requests
import csv

class web_scraping:
    def scrape(self, url):
        r = requests.get(url)

        soup = BeautifulSoup(r.content, 'html5lib') # If this line causes an error, run 'pip install html5lib' or install html5lib

        links = [link.get('href') for link in (soup.find_all('a'))]

        notebooks = []
        notebook = {}

        for l in links:
            x = l.split('/',1) 
            if x[0] == 'Notebooks':
                notebook['link'] = ('https://isomerdesign.com/PiHKAL/'+ l)
                notebooks.append(notebook)

        for n in notebooks:
            print(n)

        filename = 'notebook_links.csv'
        with open(filename, 'w', newline='') as f:
            w = csv.DictWriter(f,['link'])
            w.writeheader()
            for link in notebooks:
                w.writerow(link)
        return "done"
