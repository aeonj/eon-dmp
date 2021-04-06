/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package eon.hg.fap.flow.service;

import eon.hg.fap.flow.model.calendar.CalendarDate;
import eon.hg.fap.flow.model.calendar.CalendarDef;
import org.quartz.Calendar;

import java.util.Collection;

/**
 * @author Jacky.gao
 * @since 2013年9月23日
 */
public interface CalendarService {
	public static final String BEAN_ID="wf.calendarService";
	Collection<CalendarDef> getAllCalendarDefs();
	CalendarDef getCalendarDef(long calendarId);
	Collection<CalendarDate> getCalendarDate(long calendarId);
	Calendar getCalendar(long calendarId);
}
