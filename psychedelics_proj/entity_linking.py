import hashlib
import requests
from coreferences import PDF_Pro 

class Entity_Linking:
    def get_entities(self):
        def query_raw(text, url="https://bern.korea.ac.kr/plain"):
        #Biomedical entity linking API
            return requests.post(url, data={'sample_text': text}, verify=False).json()

        entity_list = []
        paragraphs = PDF_Pro().finalize_text()
        print(len(paragraphs), " LENGTH")
        # The last sentence is invalid
        for p in paragraphs[:-1]:
            entity_list.append(query_raw(p))

        parsed_entities = []
        for entities in entity_list:
            e = [] 
        # If there are not entities in the text
            if not entities.get('denotations'):
                parsed_entities.append({'text':entities['text'], 'text_sha256': hashlib.sha256(entities['text'].encode('utf-8')).hexdigest()})
                continue
            for entity in entities['denotations']:
                other_ids = [id for id in entity['id'] if not id.startswith("BERN")]
                entity_type = entity['obj']
                entity_name = entities['text'][entity['span']['begin']:entity['span']['end']]
                try:
                    entity_id = [id for id in entity['id'] if id.startswith("BERN")][0]
                except IndexError:
                    entity_id = entity_name
                    e.append({'entity_id': entity_id, 'other_ids': other_ids, 'entity_type': entity_type, 'entity': entity_name})
            parsed_entities.append({'entities':e, 'text':entities['text'], 'text_sha256': hashlib.sha256(entities['text'].encode('utf-8')).hexdigest()})
        return parsed_entities


entities = Entity_Linking().get_entities()
for e in entities:
    print(e)
    print("")
    for x, y in e.items():
        print(x, ": ", y)
#need to conglomerate all the e's then sort them as a whole, then output them