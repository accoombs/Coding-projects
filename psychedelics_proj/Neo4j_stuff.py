from neo4j import GraphDatabase
from NLP import NLP

class neo4j:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))
        NLP().get_entities()

    def close(self):
        self.driver.close()

    def get_nodes(self):
        query1 = (
                "LOAD CSV WITH HEADERS FROM 'file:///Mescaline.csv' AS row "
                "merge(a:SENSES {sense:'sight'}) "
                " merge(b:SENSES {sense: 'smell'})"
                "merge(c:SENSES {sense: 'hearing'}) "
                "merge(d:SENSES {sense: 'touch'}) "
                "merge(e:SENSES {sense: 'taste'}) "
                "merge (w:WORD {Word:row.WORD, Reps:row.REPS, Sight:row.SIGHT, Smell:row.SMELL, Hearing:row.HEARING, Touch:row.TOUCH, Taste:row.TASTE}) "
                "merge (m:MOLECULE {Name:row.TITLE}) "
                "merge (w)-[:ASSOCIATED_WITH]->(m)")
        query2 = (
                "match (a:SENSES {sense:'sight'}) "
                "match (v:WORD {Sight:'1'}) "
                "match (b:SENSES {sense:'smell'}) "
                "match (w:WORD {Smell:'1'}) "
                "match (c:SENSES {sense:'hearing'}) " 
                "match (x:WORD {Hearing:'1'}) "
                "match (d:SENSES {sense:'touch'})" 
                "match (y:WORD {Touch:'1'}) "
                "match (e:SENSES {sense:'taste'}) "
                "match (z:WORD {Taste:'1'}) "
                "merge(v)-[:ASSOCIATED_WITH]->(a) "
                "merge(w)-[:ASSOCIATED_WITH]->(b) " 
                "merge(x)-[:ASSOCIATED_WITH]->(c) "
                "merge(y)-[:ASSOCIATED_WITH]->(d) "
                "merge(z)-[:ASSOCIATED_WITH]->(e) "
        )

        def runQuery(tx,query):
            tx.run(query)
        
        with self.driver.session() as create_nodes:
            create_nodes.write_transaction(runQuery, query1)
        with self.driver.session() as sense_rels:
            sense_rels.write_transaction(runQuery, query2)     
        self.close()



neo4j("bolt://localhost:7687", "neo4j", "gist766PEEN").get_nodes()

"""LOAD CSV WITH HEADERS FROM 'file:///Mescaline.csv' AS row
merge(a:SENSES {sense:'sight'})
merge(b:SENSES {sense: 'smell'})
merge(c:SENSES {sense: 'hearing'})
merge(d:SENSES {sense: 'touch'})
merge(e:SENSES {sense: 'taste'})
merge (w:WORD {Word:row.WORD, Reps:row.REPS, Sight:row.SIGHT, Smell:row.SMELL, Hearing:row.HEARING, Touch:row.TOUCH, Taste:row.TASTE})
merge (m:MOLECULE {Name:row.TITLE})
merge (w)-[:ASSOCIATED_WITH]->(m)"""

"""match (a:SENSES {sense:'sight'})
match (v:WORD {Sight:'1'})
match (b:SENSES {sense:'smell'})
match (w:WORD {Smell:'1'})
match (c:SENSES {sense:'hearing'})
match (x:WORD {Hearing:'1'})
match (d:SENSES {sense:'touch'})
match (y:WORD {Touch:'1'})
match (e:SENSES {sense:'taste'})
match (z:WORD {Taste:'1'})
merge(v)-[:ASSOCIATED_WITH]->(a)
merge(w)-[:ASSOCIATED_WITH]->(b)
merge(x)-[:ASSOCIATED_WITH]->(c)
merge(y)-[:ASSOCIATED_WITH]->(d)
merge(z)-[:ASSOCIATED_WITH]->(e)"""