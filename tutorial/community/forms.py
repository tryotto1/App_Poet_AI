from django.forms import ModelForm
from community.models import *

class write_poem_form(ModelForm):
    class Meta:
        model = Poems
        fields=['my_email', 'poem_title','poem', 'poem_tag']

class singin_form(ModelForm):
    class Meta:
        model = User
        fields=['my_email', 'my_pwd', 'my_name']

class login_form(ModelForm):
    class Meta:
        model = User
        fields=['my_email', 'my_pwd']

class new_follow_form(ModelForm):
    class Meta:
        model = Following
        fields=['my_email', 'following_email']


class Form(ModelForm):
    class Meta:
        model = Article
        fields=['name', 'title', 'contents', 'url', 'email']
        





