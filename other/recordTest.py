#!/usr/bin/env python3.5
# -*- coding: utf-8 -*-

'''
    Test if the different sound cards record audio at the same time
'''

import os
import wave
import numpy as np


def get_valid_sub_file_list(base_path, postfix):
    sub_file_list = []
    all_sub = os.listdir(base_path)
    for sub in all_sub:
        if not os.path.isfile(os.path.join(base_path, sub)) or not sub.endswith(postfix):
            continue
        sub_file_list.append(sub)

    print(sub_file_list)
    return sub_file_list


def get_wave_data_start_time(file_path):
    f = wave.open(file, 'rb')
    params = f.getparams()

    channel_num, sample_width, frame_rate, frame_num = params[:4]
    wave_data_str = f.readframes(frame_num)

    wave_data = np.fromstring(wave_data_str, dtype=np.int16)

    wave_data_normalization = wave_data * 1.0 / max(abs(wave_data))  # wave幅值归一化
    indexs = np.where(abs(wave_data_normalization) > threshold_value)
    indexs_min = min(indexs[0])
    start_time = indexs_min / frame_rate
    return start_time


if __name__ == "__main__":
    base_path = r'D:\sxz\audio\output'
    postfix = 'wav'
    threshold_value = 0.5

    files = get_valid_sub_file_list(base_path, postfix)
    for file in files:
        file = base_path + os.path.sep + file
        start_time = get_wave_data_start_time(file)
        print('Index of ' + file + ' >>> ', start_time)

    print('End')


