from typing import Optional
from pydantic import BaseModel, Field   

class Expense(BaseModel):
    merchant: Optional[str] = Field(title="mercheant", description="The name of the merchant")
    amount: Optional[float] = Field(title="amount" , description="The amount of the expense made on transaction")
    currency: Optional[str] = Field(title="currency" , description="The currency of the expense")
    expense_type: Optional[str] = Field(title="expense_type" , description="The type of the expense eg: food, travel, medical, shoping, education etc")