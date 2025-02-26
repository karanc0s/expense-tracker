from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_core.prompts import ChatPromptTemplate
from flask import jsonify
from dotenv import load_dotenv
import os
from app.schema.Expense import Expense



class Scraper:
    def __init__(self):
        load_dotenv()
        API_KEY = os.getenv("GOOGLE_API_KEY")
        self.llm = ChatGoogleGenerativeAI(
            model="gemini-2.0-flash",
            temperature=0.2,
            max_retries=2,
            api_key=API_KEY,                                    
        )
        self.promptTemplate = ChatPromptTemplate.from_messages([
            ("system", 
             "You are an expert extraction algorithm. Only extract relevant information from the text. If you do not know the value of an attribute asked to extract, return null for the attribute's value.",),
            ("human", "{text}"),
        ])
        self.runnable = self.promptTemplate | self.llm.with_structured_output(schema= Expense)


    def process(self, message):
        ai_msg = self.runnable.invoke(message)
        print(ai_msg)
        return ai_msg