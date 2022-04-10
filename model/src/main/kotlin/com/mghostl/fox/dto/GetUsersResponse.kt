package com.mghostl.fox.dto

class GetUsersResponse(data: Set<UserDto>, count: Long) : GetPageableResponse<UserDto>(data, count)
