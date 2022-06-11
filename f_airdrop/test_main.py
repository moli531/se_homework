from unittest import TestCase
from main import up_video
import unittest

class Test(TestCase):
    def test_up_video(self):
        self.assertIsNotNone(up_video())

