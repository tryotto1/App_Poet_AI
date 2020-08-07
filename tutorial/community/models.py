from django.db import models



class Article(models.Model):
    name = models.CharField(max_length=50)
    title = models.CharField(max_length=50)
    contents = models.TextField()
    url = models.URLField()
    email = models.EmailField()
    cdate = models.DateTimeField(auto_now_add=True)

# Create your models here.
class User(models.Model):
    my_email = models.CharField(max_length=50)
    my_pwd = models.CharField(max_length=50)
    my_name = models.CharField(max_length=50)
    followered_num = models.IntegerField()

class Poems(models.Model):
    my_email = models.CharField(max_length=50, null=True, blank=True)    
    poem = models.TextField(null=True, blank=True)
    poem_title = models.CharField(max_length=50, null=True, blank=True)    
    poem_tag = models.CharField(max_length=50, null=True, blank=True)
    poem_likes = models.IntegerField(null=True, blank=True, default=0)
    poem_date = models.DateTimeField(auto_now_add=True, null=True, blank=True)

class Following(models.Model):
    my_email = models.CharField(max_length=50)
    following_email = models.CharField(max_length=50)