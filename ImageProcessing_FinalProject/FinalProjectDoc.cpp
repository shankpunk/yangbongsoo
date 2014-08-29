
// FinalProjectDoc.cpp : CFinalProjectDoc 클래스의 구현
//

#include "stdafx.h"
// SHARED_HANDLERS는 미리 보기, 축소판 그림 및 검색 필터 처리기를 구현하는 ATL 프로젝트에서 정의할 수 있으며
// 해당 프로젝트와 문서 코드를 공유하도록 해 줍니다.
#ifndef SHARED_HANDLERS
#include "FinalProject.h"
#endif

#include "FinalProjectDoc.h"

#include <propkey.h>

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CFinalProjectDoc

IMPLEMENT_DYNCREATE(CFinalProjectDoc, CDocument)

BEGIN_MESSAGE_MAP(CFinalProjectDoc, CDocument)
	ON_COMMAND(IDM_HISTO_STRETCH, &CFinalProjectDoc::OnHistoStretch)
	ON_COMMAND(IDM_END_IN_SEARCH, &CFinalProjectDoc::OnEndInSearch)
	ON_COMMAND(IDM_HISTOGRAM, &CFinalProjectDoc::OnHistogram)
	ON_COMMAND(IDM_HISTO_EQUAL, &CFinalProjectDoc::OnHistoEqual)
	ON_COMMAND(IDM_EMBOSSING, &CFinalProjectDoc::OnEmbossing)
	ON_COMMAND(IDM_BLURR, &CFinalProjectDoc::OnBlurr)
	ON_COMMAND(IDM_SHARPENING, &CFinalProjectDoc::OnSharpening)
	ON_COMMAND(IDM_GAUSSIAN_FILTER, &CFinalProjectDoc::OnGaussianFilter)
	ON_COMMAND(IDM_HPF_SHARP, &CFinalProjectDoc::OnHpfSharp)
	ON_COMMAND(IDM_LPF_SHARP, &CFinalProjectDoc::OnLpfSharp)
	ON_COMMAND(IDM_DIFF_OPERATOR, &CFinalProjectDoc::OnDiffOperator)
	ON_COMMAND(IDM_HOMOGEN_OPERATOR, &CFinalProjectDoc::OnHomogenOperator)
	ON_COMMAND(IDM_CHA_OPERATOR, &CFinalProjectDoc::OnChaOperator)
	ON_COMMAND(IDM_LOG, &CFinalProjectDoc::OnLog)
	ON_COMMAND(IDM_DOG, &CFinalProjectDoc::OnDog)
	ON_COMMAND(IDM_ZOOM_IN, &CFinalProjectDoc::OnZoomIn)
	ON_COMMAND(IDM_NEAREST, &CFinalProjectDoc::OnNearest)
	ON_COMMAND(IDM_BILINEAR, &CFinalProjectDoc::OnBilinear)
	ON_COMMAND(IDM_ZOOM_OUT, &CFinalProjectDoc::OnZoomOut)
	ON_COMMAND(IDM_MEDIAN_SUB, &CFinalProjectDoc::OnMedianSub)
	ON_COMMAND(IDM_SUB, &CFinalProjectDoc::OnSub)
	ON_COMMAND(IDM_TRANSLATION, &CFinalProjectDoc::OnTranslation)
	ON_COMMAND(IDM_MIRROR_HOR, &CFinalProjectDoc::OnMirrorHor)
	ON_COMMAND(IDM_MIRROR_VER, &CFinalProjectDoc::OnMirrorVer)
	ON_COMMAND(IDM_ROTATION, &CFinalProjectDoc::OnRotation)
	ON_COMMAND(IDM_MEAN_FILTER, &CFinalProjectDoc::OnMeanFilter)
	ON_COMMAND(IDM_MEDIAN_FILTER, &CFinalProjectDoc::OnMedianFilter)
	ON_COMMAND(IDM_MAX_FILTER, &CFinalProjectDoc::OnMaxFilter)
	ON_COMMAND(IDM_MIN_FILTER, &CFinalProjectDoc::OnMinFilter)
	ON_COMMAND(IDM_ZOOM_IN_C, &CFinalProjectDoc::OnZoomInC)
END_MESSAGE_MAP()


// CFinalProjectDoc 생성/소멸

CFinalProjectDoc::CFinalProjectDoc()
	: m_InputImage(NULL)
	, m_height(0)
	, m_width(0)
	, m_OutputImage(NULL)
	, m_Re_height(0)
	, m_Re_width(0)
	, m_InputImageR(NULL)
	, m_InputImageG(NULL)
	, m_InputImageB(NULL)
	, m_OutputImageR(NULL)
	, m_OutputImageG(NULL)
	, m_OutputImageB(NULL)
{
	// TODO: 여기에 일회성 생성 코드를 추가합니다.

}

CFinalProjectDoc::~CFinalProjectDoc()
{
}

BOOL CFinalProjectDoc::OnNewDocument()
{
	if (!CDocument::OnNewDocument())
		return FALSE;

	// TODO: 여기에 재초기화 코드를 추가합니다.
	// SDI 문서는 이 문서를 다시 사용합니다.

	return TRUE;
}




// CFinalProjectDoc serialization

void CFinalProjectDoc::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		// TODO: 여기에 저장 코드를 추가합니다.
	}
	else
	{
		// TODO: 여기에 로딩 코드를 추가합니다.
	}
}

#ifdef SHARED_HANDLERS

// 축소판 그림을 지원합니다.
void CFinalProjectDoc::OnDrawThumbnail(CDC& dc, LPRECT lprcBounds)
{
	// 문서의 데이터를 그리려면 이 코드를 수정하십시오.
	dc.FillSolidRect(lprcBounds, RGB(255, 255, 255));

	CString strText = _T("TODO: implement thumbnail drawing here");
	LOGFONT lf;

	CFont* pDefaultGUIFont = CFont::FromHandle((HFONT) GetStockObject(DEFAULT_GUI_FONT));
	pDefaultGUIFont->GetLogFont(&lf);
	lf.lfHeight = 36;

	CFont fontDraw;
	fontDraw.CreateFontIndirect(&lf);

	CFont* pOldFont = dc.SelectObject(&fontDraw);
	dc.DrawText(strText, lprcBounds, DT_CENTER | DT_WORDBREAK);
	dc.SelectObject(pOldFont);
}

// 검색 처리기를 지원합니다.
void CFinalProjectDoc::InitializeSearchContent()
{
	CString strSearchContent;
	// 문서의 데이터에서 검색 콘텐츠를 설정합니다.
	// 콘텐츠 부분은 ";"로 구분되어야 합니다.

	// 예: strSearchContent = _T("point;rectangle;circle;ole object;");
	SetSearchContent(strSearchContent);
}

void CFinalProjectDoc::SetSearchContent(const CString& value)
{
	if (value.IsEmpty())
	{
		RemoveChunk(PKEY_Search_Contents.fmtid, PKEY_Search_Contents.pid);
	}
	else
	{
		CMFCFilterChunkValueImpl *pChunk = NULL;
		ATLTRY(pChunk = new CMFCFilterChunkValueImpl);
		if (pChunk != NULL)
		{
			pChunk->SetTextValue(PKEY_Search_Contents, value, CHUNK_TEXT);
			SetChunkValue(pChunk);
		}
	}
}

#endif // SHARED_HANDLERS

// CFinalProjectDoc 진단

#ifdef _DEBUG
void CFinalProjectDoc::AssertValid() const
{
	CDocument::AssertValid();
}

void CFinalProjectDoc::Dump(CDumpContext& dc) const
{
	CDocument::Dump(dc);
}
#endif //_DEBUG


// CFinalProjectDoc 명령

#include <math.h>
BOOL CFinalProjectDoc::OnOpenDocument(LPCTSTR lpszPathName)
{
	if (!CDocument::OnOpenDocument(lpszPathName))
		return FALSE;

	// TODO:  여기에 특수화된 작성 코드를 추가합니다.
	CFile File;
	File.Open(lpszPathName, CFile::modeRead | CFile::typeBinary);
	int i;
	// (수정) 2차원배열로사용하기위함- 정방형이미지만다룸.
	double fLength = (double) File.GetLength();
	double log2Value = log(sqrt(fLength)) / log(2.0);

	if ( log2Value == (int)log2Value  )
	{
		m_height = m_width = (long)sqrt(fLength);
	} 
	else {
		AfxMessageBox(L"정방향크기의이미지만지원함");
		return 0;
	}

	m_InputImage = new unsigned char* [m_height];
	for (i=0; i<m_height; i++)
		m_InputImage[i] = new unsigned char [m_width];

	for (i=0; i<m_height; i++)
		File.Read(m_InputImage[i], m_width);

	File.Close();
	return TRUE;

}


void CFinalProjectDoc::OnCloseDocument()
{
	// TODO: 여기에 특수화된 코드를 추가 및/또는 기본 클래스를 호출합니다.
	int i;
	for (i=0; i<m_height; i++)
		delete m_InputImage[i];
	delete m_InputImage;

	if (m_OutputImage != NULL) {
		for (i=0; i<m_Re_height; i++)
			delete m_OutputImage[i];
		delete m_OutputImage;
	}
	CDocument::OnCloseDocument();
}


BOOL CFinalProjectDoc::OnSaveDocument(LPCTSTR lpszPathName)
{
	// TODO: 여기에 특수화된 코드를 추가 및/또는 기본 클래스를 호출합니다.
	CFile File;
	CFileDialog  SaveDlg(FALSE, L"raw", NULL, OFN_HIDEREADONLY);

	if(SaveDlg.DoModal() == IDOK) {
		File.Open(SaveDlg.GetPathName(), CFile::modeCreate | CFile::modeWrite);
		for (int i=0; i<m_Re_height; i++)
			File.Write(m_OutputImage[i], m_Re_width);
		File.Close();
	}
	return true;

}


void CFinalProjectDoc::OnHistoStretch()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j;
	int MIN,MAX;

	//최소 최대 명도값 찾기
	MIN = MAX = m_InputImage[0][0];
	for(i=0;i<m_height;i++){
		for(j=0;j<m_width;j++){
			if(m_InputImage[i][j] < MIN) MIN = m_InputImage[i][j];
			if(m_InputImage[i][j] > MAX) MAX = m_InputImage[i][j];
		}
	}

	// 새로운 값 구하기 
	//이제부터는 왠만하면 실수로 처리해라 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			(unsigned char)(m_OutputImage[i][j] = (m_InputImage[i][j] - MIN) * 255.0 / (MAX - MIN));
		}
	}

}


void CFinalProjectDoc::OnEndInSearch()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j;
	int MIN,MAX;
	int MIN2,MAX2;
	//최소 최대 명도값 찾기
	MIN = MAX = m_InputImage[0][0];
	for(i=0;i<m_height;i++){
		for(j=0;j<m_width;j++){
			if(m_InputImage[i][j] < MIN) MIN = m_InputImage[i][j];
			if(m_InputImage[i][j] > MAX) MAX = m_InputImage[i][j];
		}
	}

	MIN2 = MIN + 50;
	MAX2 = MAX - 50;

	// 새로운 값 구하기 
	//이제부터는 왠만하면 실수로 처리해라 
	int value; 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			value = m_InputImage[i][j];
			if(value < MIN2) m_OutputImage[i][j] = 0;
			else if(value > MAX2) m_OutputImage[i][j] = 255;
			else
				(unsigned char)(m_OutputImage[i][j] = (m_InputImage[i][j] - MIN2) * 255.0 / (MAX2 - MIN2));
		}
	}
}

double m_HIST[256]; //원 막대그래프 
unsigned char m_Scale_HIST[256]; // 정규화된 막대그래프 
double m_Sum_Of_HIST[256];//누적 막대그래프 
void CFinalProjectDoc::OnHistogram()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	//인풋 영상을 가지고 막대그리프를 그리는 작업이다. 
	//그래서 256 256 으로 고정 시킨다. 
	m_Re_height=256;
	m_Re_width=256;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j ,value;
	double MIN, MAX;

	//막대그래프 만들기(빈도수 조사) 
	for(i=0;i<256;i++) m_HIST[i] =0;

	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {
			value = m_InputImage[i][j];
			m_HIST[value] ++; 
		}
	}
	//정규화된 막대그래프 
	//MIN = MAX = m_InputImage[i][j] = 0 ; //지금은 영상이 아니라 막대그래프를 정규화시켜야돼 그래서 지금 이 줄은 틀림
	MIN = MAX = m_HIST[0];

	for(i=0;i<256;i++){
		if(m_HIST[i] < MIN) MIN = m_HIST[i];
		if(m_HIST[i] > MAX) MAX = m_HIST[i];
	}

	for(i=0;i<256;i++){
		(unsigned char)(m_Scale_HIST[i] = (m_HIST[i] - MIN) * 255.0 / (MAX-MIN)) ;
	}
	//출력 영상 초기화 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			m_OutputImage[i][j] =255; 
		}
	}
	//화면 출력 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Scale_HIST[i]; j++) {
			m_OutputImage[255-j][i] = 0;
		}
	}
}


void CFinalProjectDoc::OnHistoEqual()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j,value;
	double SUM = 0.0; 
	//1단계 : 막대그래프 만들기(빈도수 조사) 
	for(i=0;i<256;i++) m_HIST[i] =0;

	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {
			value = m_InputImage[i][j];
			m_HIST[value] ++; 
		}
	}

	//2단계 : 누적 막대그래프 sum[i]
	for(i=0;i<256;i++){
		SUM = SUM + m_HIST[i];
		m_Sum_Of_HIST[i] = SUM; 
	}

	//3단계 : 정규화된 누적 막대그래프 n[i] = sum[i] * 255 / (m_width*m_height)
	int temp; 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			temp = m_InputImage[i][j]; 

			m_OutputImage[i][j] = (unsigned char)(m_Sum_Of_HIST[temp] * 255.0 / (m_width * m_height));
		}
	}
}

double **m_TempImage;
void CFinalProjectDoc::OnEmbossing()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	double EmboMask[3][3] = { {1. , 0. , 0. }, 
							{0. , 0. , 0. }, 
							{0. , 0. , -1. } };

	m_TempImage = OnMaskProcess(EmboMask);

	//128더하기 (왜? 어두우니까)
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++)
			m_TempImage[i][j] += 128.0;

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_TempImage[i][j] > 255.0) m_TempImage[i][j] = 255.0;
			if(m_TempImage[i][j] < 0.0) m_TempImage[i][j] = 0.0;
		}
	}
	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = (unsigned char)m_TempImage[i][j];
	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete m_TempImage[i];
	}
	delete m_TempImage;
}


double** CFinalProjectDoc::OnMaskProcess(double Mask[3][3])
{
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int i,j,n,m;

	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempInputImage[i+1][j+1] = m_InputImage[i][j];

	//진짜 회선처리 
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {

			for(n=0; n<3; n++){
				for(m=0;m<3;m++){
					S = S+ Mask[n][m]* tempInputImage[n+i][m+j];		
				}
			}
			tempOutputImage[i][j] = S;  
			S = 0.0; 
		}
	}

	for(i=0;i<m_height+2;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;

	return tempOutputImage;
}

#include "ConstantDlg.h"
double **m_TempImage2;
void CFinalProjectDoc::OnBlurr()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		m_Re_height=m_height;
		m_Re_width=m_width;
		int i,j;
		// 출력이미지 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		// 출력 이미지 영상 처리 <핵심 알고리즘>
		double **BlurrMask;
		int size = (int)dlg.m_Constant; //내가 입력한 숫자가 size 변수에 담길것이고

		BlurrMask = new double*[size]; // 사이즈만큼 mask 배열을 만들어서 
		for(i=0;i<size;i++)
			BlurrMask[i] = new double[size];

		for(i=0;i<size;i++) //이걸 갯수만큼으로 해야돼 ... 
			for(j=0;j<size;j++)
				BlurrMask[i][j] = 1. / (size*size) ;

		m_TempImage2 = OnMaskProcess2(BlurrMask,size);
		//0 ~ 255로 처리
		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				if(m_TempImage2[i][j] > 255.0) m_TempImage2[i][j] = 255.0;
				if(m_TempImage2[i][j] < 0.0) m_TempImage2[i][j] = 0.0;
			}
		}
		//tempOutputImage -->  m_OutputImage 
		for(i=0; i<m_Re_height; i++) 
			for(j=0; j<m_Re_width; j++) 
				m_OutputImage[i][j] = (unsigned char)m_TempImage2[i][j];

		//메모리 해제 
		for(i=0;i<m_Re_height;i++){
			delete m_TempImage2[i];
		}
		delete m_TempImage2;
	}
}


double** CFinalProjectDoc::OnMaskProcess2(double** Mask, int maskSize)
{
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int i,j,n,m;

	//크기 설정도 가변으로 
	int addPix = (maskSize / 2) * 2; //정수화 시키기기 
	int start = addPix / 2;

	//메모리 할당 
	tempInputImage = new double* [m_height+addPix];
	for (int i=0; i<m_height+addPix; i++)
		tempInputImage[i] = new double [m_width+addPix];

	//임시 인풋 초기화 
	for(i=0; i<m_height+addPix; i++) 
		for(j=0; j<m_width+addPix; j++) 
			tempInputImage[i][j] = 127.0;

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {
			tempInputImage[i+start][j+start] = m_InputImage[i][j];
		}
	}
	//진짜 회선처리 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {

			for(n=0; n<maskSize; n++)
				for(m=0;m<maskSize;m++)
					S = S+ Mask[n][m] * tempInputImage[n+i][m+j];	

			tempOutputImage[i][j] = S;  
			S = 0.0; 
		}
	}

	for(i=0;i<m_height+addPix;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;

	return tempOutputImage;
}


void CFinalProjectDoc::OnSharpening()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	double SharpeningMask[3][3] = {	{-1. , -1. , -1. }, 
									{-1. , 9. , -1. }, 
									{-1. , -1. , -1. } };

	m_TempImage = OnMaskProcess(SharpeningMask);

	//128더하기 (왜? 어두우니까)
	//for(i=0; i<m_Re_height; i++) {
	//	for(j=0; j<m_Re_width; j++) {
	//		m_TempImage[i][j] += 128.0;
	//	}
	//}

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_TempImage[i][j] > 255.0) m_TempImage[i][j] = 255.0;
			if(m_TempImage[i][j] < 0.0) m_TempImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {

			m_OutputImage[i][j] = (unsigned char)m_TempImage[i][j];
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete m_TempImage[i];
	}
	delete m_TempImage;
}


void CFinalProjectDoc::OnGaussianFilter()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	double GaussianMask[3][3] = {	{1./16. , 1./8. , 1./16. }, 
									{1./8. , 1./4. , 1./8. }, 
									{1./16. , 1./8. , 1./16. } };

	m_TempImage = OnMaskProcess(GaussianMask);

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_TempImage[i][j] > 255.0) m_TempImage[i][j] = 255.0;
			if(m_TempImage[i][j] < 0.0) m_TempImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			m_OutputImage[i][j] = (unsigned char)m_TempImage[i][j];
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete m_TempImage[i];
	}
	delete m_TempImage;
}


void CFinalProjectDoc::OnHpfSharp()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	double HpfSharpMask[3][3] = {	{-1./9. , -1./9. , -1./9. }, 
									{-1./9. , 8./9. , -1./9. }, 
									{-1./9. , -1./9. , -1./9. } };

	m_TempImage = OnMaskProcess(HpfSharpMask);

	//128더하기 (왜? 어두우니까)
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_TempImage[i][j] += 128.0;

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_TempImage[i][j] > 255.0) m_TempImage[i][j] = 255.0;
			if(m_TempImage[i][j] < 0.0) m_TempImage[i][j] = 0.0;
		}
	}
	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			m_OutputImage[i][j] = (unsigned char)m_TempImage[i][j];
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete m_TempImage[i];
	}
	delete m_TempImage;
}


void CFinalProjectDoc::OnLpfSharp()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	double alpha = 1.0;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	double LpfSharpMask[3][3] = {	{1./9. , 1./9. , 1./9. }, 
									{1./9. , 1./9. , 1./9. }, 
									{1./9. , 1./9. , 1./9. } };

	m_TempImage = OnMaskProcess(LpfSharpMask);

	for(i=0;i<m_Re_height;i++)
		for(j=0;j<m_Re_width;j++)
			m_TempImage[i][j] = (alpha * m_InputImage[i][j]) - (unsigned char)m_TempImage[i][j];

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_TempImage[i][j] > 255.0) m_TempImage[i][j] = 255.0;
			if(m_TempImage[i][j] < 0.0) m_TempImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = (unsigned char)m_TempImage[i][j];

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete m_TempImage[i];
	}
	delete m_TempImage;
}


void CFinalProjectDoc::OnDiffOperator()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	double myMask[3][3] = { {0. , -1. , 0. }, 
							{-1. , 2. , 0. }, 
							{0. , 0. , 0. } };

	m_TempImage = OnMaskProcess(myMask);

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_TempImage[i][j] > 255.0) m_TempImage[i][j] = 255.0;
			if(m_TempImage[i][j] < 0.0) m_TempImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = (unsigned char)m_TempImage[i][j];

	//임계값 처리 (옵션)
	int eValue = 20; //이거 다이얼로그해서 내가 입력받게 해야함 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(m_OutputImage[i][j] >= eValue ) m_OutputImage[i][j] = 255;
			if(m_OutputImage[i][j] < eValue ) m_OutputImage[i][j] = 0;
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete m_TempImage[i];
	}
	delete m_TempImage;
}


void CFinalProjectDoc::OnHomogenOperator()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	//마스크 처리 함수 호출 .... 안하고 여기서 할거다 
	///////////////////////////////////////
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int n,m;
	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for(int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempInputImage[i+1][j+1] = m_InputImage[i][j];

	//진짜 회선처리 
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempOutputImage[i][j] = FindMaxValue(tempInputImage,i,j);

	for(i=0;i<m_height+2;i++)
		delete tempInputImage[i];

	delete tempInputImage;
	//////////////////////////////////////

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(tempOutputImage[i][j] > 255.0) tempOutputImage[i][j] = 255.0;
			if(tempOutputImage[i][j] < 0.0) tempOutputImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			m_OutputImage[i][j] = (unsigned char)tempOutputImage[i][j];
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete tempOutputImage[i];
	}
	delete tempOutputImage;
}


double CFinalProjectDoc::FindMaxValue(double** InImage, int x, int y)
{
	int n,m;
	double max = 0.0 , absValue;

	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			absValue = fabs( InImage[x+1][y+1] - InImage[x+n][y+m]);
			if(absValue > max) max = absValue;
		}
	}

	return max;
}


void CFinalProjectDoc::OnChaOperator()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int n,m;
	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) {
		for(j=0; j<m_width; j++) {
			tempInputImage[i+1][j+1] = m_InputImage[i][j];
		}
	}

	//진짜 회선처리 
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempOutputImage[i][j] = FindMaxValueCha(tempInputImage,i,j);

	for(i=0;i<m_height+2;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;
	//////////////////////////////////////

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(tempOutputImage[i][j] > 255.0) tempOutputImage[i][j] = 255.0;
			if(tempOutputImage[i][j] < 0.0) tempOutputImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {

			m_OutputImage[i][j] = (unsigned char)tempOutputImage[i][j];
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete tempOutputImage[i];
	}
	delete tempOutputImage;
}

double CFinalProjectDoc::FindMaxValueCha(double** InImage, int x, int y)
{
	int n,m;
	double max = 0.0 , absValue1, absValue2, absValue3, absValue4;

	absValue1 = fabs(InImage[x][y] - InImage[x+2][y+2]);
	max = absValue1; 

	absValue2 = fabs(InImage[x][y+2] - InImage[x+2][y]);
	if(absValue2 > max)
		max = absValue2;

	absValue3 = fabs(InImage[x][y+1] - InImage[x+2][y+1]);
	if(absValue3 > max)
		max = absValue3;

	absValue4 = fabs(InImage[x+1][y+2] - InImage[x+1][y]);
	if(absValue4 > max)
		max = absValue4;

	return max;
}



void CFinalProjectDoc::OnLog()
{
	////////////////////////////////////////////
	double ConMask[5][5] = {	{ 0. , 0. , -1. , 0. , 0. }, 
								{ 0. , -1. , -2. , -1. , 0. },
								{ -1. , -2. , 16. , -2. , -1. },
								{ 0. , -1. , -2. , -1. , 0. },
								{ 0. , 0. , -1. , 0. , 0. } };
	////////////////////////////////////////////
		m_Re_height=m_height;
		m_Re_width=m_width;
		int i,j;
		// 출력이미지 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		// 출력 이미지 영상 처리 <핵심 알고리즘>
		double **LogMask;
		int size = 5; //내가 입력한 숫자가 size 변수에 담길것이고

		LogMask = new double*[size]; // 사이즈만큼 mask 배열을 만들어서 
		for(i=0;i<size;i++)
			LogMask[i] = new double[size];

		for(i=0;i<size;i++){ //이걸 갯수만큼으로 해야돼 ... 
			for(j=0;j<size;j++){
				LogMask[i][j] = ConMask[i][j] ;
			}
		}
		m_TempImage2 = OnMaskProcess2(LogMask,size);
		//0 ~ 255로 처리
		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				if(m_TempImage2[i][j] > 255.0) m_TempImage2[i][j] = 255.0;
				if(m_TempImage2[i][j] < 0.0) m_TempImage2[i][j] = 0.0;
			}
		}
		//tempOutputImage -->  m_OutputImage 
		for(i=0; i<m_Re_height; i++) 
			for(j=0; j<m_Re_width; j++) 
				m_OutputImage[i][j] = (unsigned char)m_TempImage2[i][j];

		//메모리 해제 
		for(i=0;i<m_Re_height;i++){
			delete m_TempImage2[i];
		}
		delete m_TempImage2;
	
}




void CFinalProjectDoc::OnDog()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	////////////////////////////////////////////
	double ConMask[7][7] = {	{ 0. , 0. , -1. , -1. , -1. , 0. , 0.}, 
								{ 0. , -2. , -3. , -3. , -3. , -2. , 0.},
								{ -1. , -3. , 5. , 5. , 5. , -3. , -1.},
								{ -1. , -3. , 5. , 16. , 5. , -3. , -1.},
								{ -1. , -3. , 5. , 5. , 5. , -3. , -1.},
								{ 0. , -2. , -3. , -3. , -3. , -2. , 0.},
								{ 0. , 0. , -1. , -1. , -1. , 0. , 0.},
								};
	///////////////////////////////////////////

		m_Re_height=m_height;
		m_Re_width=m_width;
		int i,j;
		// 출력이미지 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		// 출력 이미지 영상 처리 <핵심 알고리즘>
		double **BlurrMask;
		int size = 7; //내가 입력한 숫자가 size 변수에 담길것이고

		BlurrMask = new double*[size]; // 사이즈만큼 mask 배열을 만들어서 
		for(i=0;i<size;i++)
			BlurrMask[i] = new double[size];

		for(i=0;i<size;i++){ //이걸 갯수만큼으로 해야돼 ... 
			for(j=0;j<size;j++){
				BlurrMask[i][j] = ConMask[i][j] ;
			}
		}

		m_TempImage2 = OnMaskProcess2(BlurrMask,size);

		//0 ~ 255로 처리
		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				if(m_TempImage2[i][j] > 255.0) m_TempImage2[i][j] = 255.0;
				if(m_TempImage2[i][j] < 0.0) m_TempImage2[i][j] = 0.0;
			}
		}

		//tempOutputImage -->  m_OutputImage 
		for(i=0; i<m_Re_height; i++) 
			for(j=0; j<m_Re_width; j++) 
				m_OutputImage[i][j] = (unsigned char)m_TempImage2[i][j];

		//메모리 해제 
		for(i=0;i<m_Re_height;i++){
			delete m_TempImage2[i];
		}
		delete m_TempImage2;
	
}


void CFinalProjectDoc::OnZoomIn()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.m_Constant;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		// 전방향으로 할거니까 기준이 inputimage에 있다. 
		int value;
		for(i=0; i<m_height; i++) {
			for(j=0; j<m_width; j++) {
				m_OutputImage[i*scale][j*scale] = m_InputImage[i][j];
			}
		}
	}
	return;
}


void CFinalProjectDoc::OnNearest()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.m_Constant;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		// 역방향이니까 outputimage가 기준으로 
		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				m_OutputImage[i][j] = m_InputImage[i/scale][j/scale];
			}
		}
	}
	return;
}


void CFinalProjectDoc::OnBilinear()
{
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.m_Constant;

		m_Re_height = m_height * scale;
		m_Re_width = m_width * scale;

		// 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		// 영상처리 알고리즘 핵심 
		double c1,c2,c3,c4; // 입력영상의 네 픽셀값 
		double x,y; 
		double r_H, r_W; //실제 실수위치
		int i_H, i_W;  // 실제 정수위치 
		double N; // 실제 구하고 싶은 값 

		for(i=0; i<m_Re_height; i++) {
			for(j=0; j<m_Re_width; j++) {
				r_H = i / (double)scale; 
				r_W = j / (double)scale;
				i_H = (int)r_H;
				i_W = (int)r_W;
				x = r_W - i_W;
				y = r_H - i_H;
				if( i_H < 0 || i_H >= (m_height-1) ||
					i_W < 0 || i_W >= (m_width-1) ){
						m_OutputImage[i][j] = 255;
				}
				else{
					c1 = m_InputImage[i_H][i_W];
					c2 = m_InputImage[i_H][i_W+1];
					c3 = m_InputImage[i_H+1][i_W+1];
					c4 = m_InputImage[i_H+1][i_W];

					N = c1*(1-x)*(1-y) + c2*x*(1-y) + c3*x*y + c4*(1-x)*y ;

					m_OutputImage[i][j] = (unsigned char) N;
				}
			}
		}
	}
	return;
}


void CFinalProjectDoc::OnZoomOut()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.m_Constant;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		for(i=0; i<m_height; i++) {
			for(j=0; j<m_width; j++) {
				m_OutputImage[i/scale][j/scale] = m_InputImage[i][j];
			}
		}
	}
	return;
}


void CFinalProjectDoc::OnMedianSub()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.m_Constant;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		int mSize = scale; 
		double **Mask;

		Mask = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask[i] = new double[mSize];

		int n,m; 

		for(i=0; i<m_height - (mSize/2); i+=mSize) {
			for(j=0; j<m_width - (mSize/2) ; j+=mSize) {
				// 마스크 채우기 	
				for(n=0;n<mSize;n++){
					for(m=0;m<mSize;m++){
						Mask[n][m] = m_InputImage[i+n][j+m]; 
					}
				}		

				m_OutputImage[i/mSize][j/mSize] = OnFindMedian(Mask, mSize);
			}
		}
	}
	return;
}


unsigned char CFinalProjectDoc::OnFindMedian(double** Mask, int mSize)
{
	int i,j, index=0;
	double *Mask1;
	Mask1 = new double[mSize * mSize]; 

	//2차원을 -> 1차원으로 
	for(i=0;i<mSize;i++){
		for(j=0;j<mSize;j++){
			Mask1[index++] = Mask[i][j];
		}
	}

	//셀렉트정렬 
	for(i=0; i<mSize * mSize; i++){
		for(j=0; j<mSize * mSize -1 ; j++){
			if(Mask1[j] > Mask1[j+1]){
				double temp = Mask1[j];
				Mask1[j] = Mask1[j+1];
				Mask1[j+1] = temp; 
			}
		}
	}

	return (unsigned char)Mask1[mSize * mSize / 2];
}


void CFinalProjectDoc::OnSub() 
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	int i,j;
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK) {

		int scale = (int)dlg.m_Constant;

		m_Re_height = m_height / scale;
		m_Re_width = m_width / scale;

		// 메모리 할당
		m_OutputImage = new unsigned char* [m_Re_height];
		for (i=0; i<m_Re_height; i++)
			m_OutputImage[i] = new unsigned char [m_Re_width];

		int mSize = scale; 
		double **Mask;

		Mask = new double *[mSize];
		for(i=0;i<mSize;i++)
			Mask[i] = new double[mSize];

		int n,m; 

		for(i=0; i<m_height - (mSize/2); i+=mSize) {
			for(j=0; j<m_width - (mSize/2) ; j+=mSize) {
				// 마스크 채우기 	
				for(n=0;n<mSize;n++){
					for(m=0;m<mSize;m++){
						Mask[n][m] = m_InputImage[i+n][j+m]; 
					}
				}		
				m_OutputImage[i/mSize][j/mSize] = OnFindAVG(Mask, mSize);
			}
		}
	}
	return;
}


unsigned char CFinalProjectDoc::OnFindAVG(double** Mask, int mSize)
{
	int i,j;
	double sum = 0.0, max = 0.0;
	//double *Mask1;
	//Mask1 = new double[mSize * mSize]; 

	for(i=0;i<mSize;i++){
		for(j=0;j<mSize;j++){
			sum += Mask[i][j] ;  
		}
	}

	max = sum / (mSize *mSize) ;

	return max;
}

#include "ConstantDlg2.h"
void CFinalProjectDoc::OnTranslation()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg2 dlg;
	if(dlg.DoModal() == IDOK){
	
	m_Re_height=m_height;
	m_Re_width=m_width;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j;
	int dx = dlg.yValue;
	int	dy= dlg.xValue; //x,y값 이동하려는 값 

	for(i=0; i<m_height; i++) { //전방향으로 하겠다
		for(j=0; j<m_width; j++) {
			//벗어나는 얘들을 처리해야함 
			if(i+dx >= 0 && i+dx < m_Re_height && j+dy >= 0 && j+dy < m_Re_width)
				m_OutputImage[i+dx][j+dy] = m_InputImage[i][j];
		}
	}
	}
}


void CFinalProjectDoc::OnMirrorHor()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j;

	for(i=0; i<m_height; i++){ //전방향으로 하겠다
		for(j=0; j<m_width; j++) {

			m_OutputImage[i][m_width-j-1] = m_InputImage[i][j];
		}
	}
}


void CFinalProjectDoc::OnMirrorVer()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	int i,j;

	for(i=0; i<m_height; i++){ //전방향으로 하겠다
		for(j=0; j<m_width; j++) {

			m_OutputImage[m_height-i-1][j] = m_InputImage[i][j];
		}
	}
}


void CFinalProjectDoc::OnRotation()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	CConstantDlg dlg;
	if(dlg.DoModal() == IDOK){
	int angle = dlg.m_Constant; //각도 입력
	double r = angle * 3.141592 / 180.0 ; //각도를 라디안 값으로 변환 
	double r90 = (90-angle) * 3.141592 / 180.0 ;
	m_Re_height = m_height * cos(r) + m_width * cos(r90);
	m_Re_width = m_height * cos(r90) + m_width * cos(r);
	int i,j;

	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = 255;

	//임시 입력 이미지 
	double **m_TempImage;
	m_TempImage = new double*[m_Re_height];
	for(i=0; i<m_Re_height;i++)
		m_TempImage[i] = new double[m_Re_width];

	for(i=0; i<m_Re_height; i++)
		for(j=0; j<m_Re_width; j++) 
			m_TempImage[i][j] = 255;

	//원 영상 -> 임시 
	int dx = (m_Re_height - m_height) /2;
	int dy = (m_Re_width - m_width) /2;

	for(i=0; i<m_height; i++)
		for(j=0; j<m_width; j++)
			m_TempImage[i+dx][j+dy] = m_InputImage[i][j];

	int x,y, xd, yd; 
	int cx = m_Re_height /2 , cy = m_Re_height /2; //기준점 설정

	for(i=0; i<m_Re_height; i++){ 
		for(j=0; j<m_Re_width; j++) {
			x=i;
			y=j; 

			xd = ((x-cx)*cos(r) + (y-cy)*sin(r)) + cx; // 다 끝나고 다시 cx cy 원상값으로 설정 
			yd = (- (x-cx)*sin(r) + (y-cy)*cos(r)) + cy;

			if(xd >= 0 && xd < m_Re_height && yd >= 0 && yd < m_Re_width)
				m_OutputImage[x][y] = m_TempImage[xd][yd];
		}
	}
	}
}


void CFinalProjectDoc::OnMeanFilter()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int n,m;

	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempInputImage[i+1][j+1] = m_InputImage[i][j];

	//진짜 회선처리 
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempOutputImage[i][j] = FindAVGValue(tempInputImage,i,j);

	for(i=0;i<m_height+2;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;
	//////////////////////////////////////

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(tempOutputImage[i][j] > 255.0) tempOutputImage[i][j] = 255.0;
			if(tempOutputImage[i][j] < 0.0) tempOutputImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = (unsigned char)tempOutputImage[i][j];



	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete tempOutputImage[i];
	}
	delete tempOutputImage;
}


double CFinalProjectDoc::FindAVGValue(double** InImage, int x, int y)
{
	int n,m;
	double max = 0.0 , sum=0.0;

	for(n=0;n<3;n++){
		for(m=0;m<3;m++){

			sum += InImage[n+x][m+y];
		}
	}
	max = sum / 9.0;

	return max;
}


void CFinalProjectDoc::OnMedianFilter()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>

	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int n,m;

	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempInputImage[i+1][j+1] = m_InputImage[i][j];

	//진짜 회선처리 
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempOutputImage[i][j] = FindMIDValue(tempInputImage,i,j);

	for(i=0;i<m_height+2;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;
	//////////////////////////////////////

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(tempOutputImage[i][j] > 255.0) tempOutputImage[i][j] = 255.0;
			if(tempOutputImage[i][j] < 0.0) tempOutputImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {

			m_OutputImage[i][j] = (unsigned char)tempOutputImage[i][j];
		}
	}

	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete tempOutputImage[i];
	}
	delete tempOutputImage;
}


double CFinalProjectDoc::FindMIDValue(double** InImage, int x, int y)
{
	int n,m;
	double result = 0.0 , sum=0.0;
	double tempArray[9] = {0.0}; //정렬할 배열 선언, 초기화
	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			tempArray[(3*n)+m] = InImage[n+x][m+y];
		}
	}
	//temp 가지고 버블소팅해서 가운데값 추출하고 
	for(int i=0; i<8;i++){
		for(int j=0; j<8-i;j++){
			if(tempArray[j] > tempArray[j+1]){
				int temp = tempArray[j];
				tempArray[j] = tempArray[j+1];
				tempArray[j+1] = temp; 
			}
		}
	}
	result = tempArray[4]; //중간값 선택

	//temp 초기화 시키고 
	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			tempArray[(3*n)+m] = 0.0;
		}
	}

	return result;
}


void CFinalProjectDoc::OnMaxFilter()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int n,m;

	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempInputImage[i+1][j+1] = m_InputImage[i][j];

	//진짜 회선처리 
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempOutputImage[i][j] = FindMAXValue2(tempInputImage,i,j);

	for(i=0;i<m_height+2;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;
	//////////////////////////////////////

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(tempOutputImage[i][j] > 255.0) tempOutputImage[i][j] = 255.0;
			if(tempOutputImage[i][j] < 0.0) tempOutputImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = (unsigned char)tempOutputImage[i][j];



	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete tempOutputImage[i];
	}
	delete tempOutputImage;

}


double CFinalProjectDoc::FindMAXValue2(double** InImage, int x, int y)
{
	int n,m;
	double result = 0.0 , sum=0.0;
	int index = 0;
	double tempArray[9] = {0.0}; //정렬할 배열 선언, 초기화
	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			//tempArray[(3*n)+m] = InImage[n+x][m+y];
			tempArray[index++] = InImage[n+x][m+y];
		}
	}
	//temp 가지고 버블소팅해서 가운데값 추출하고 
	for(int i=0; i<8;i++){
		for(int j=0; j<8-i;j++){
			if(tempArray[j] > tempArray[j+1]){
				int temp = tempArray[j];
				tempArray[j] = tempArray[j+1];
				tempArray[j+1] = temp; 
			}
		}
	}
	result = tempArray[8]; //최대값 선택

	//temp 초기화 시키고 
	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			tempArray[(3*n)+m] = 0.0;
		}
	}

	return result;
}

void CFinalProjectDoc::OnMinFilter()
{
	// TODO: 여기에 명령 처리기 코드를 추가합니다.
	m_Re_height=m_height;
	m_Re_width=m_width;
	int i,j;
	// 출력이미지 메모리 할당
	m_OutputImage = new unsigned char* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		m_OutputImage[i] = new unsigned char [m_Re_width];

	// 출력 이미지 영상 처리 <핵심 알고리즘>
	//임시 입출력 배열, S는 마스크 연산 결과 
	double **tempInputImage, **tempOutputImage, S=0.0 ;
	int n,m;

	//메모리 할당 
	tempInputImage = new double* [m_height+2];
	for (int i=0; i<m_height+2; i++)
		tempInputImage[i] = new double [m_width+2];

	tempOutputImage = new double* [m_Re_height];
	for (int i=0; i<m_Re_height; i++)
		tempOutputImage[i] = new double [m_Re_width];

	//m_inputImage --> tempInputImage 임시인풋에 옮기기
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempInputImage[i+1][j+1] = m_InputImage[i][j];

	//진짜 회선처리 
	for(i=0; i<m_height; i++) 
		for(j=0; j<m_width; j++) 
			tempOutputImage[i][j] = FindMINValue2(tempInputImage,i,j);

	for(i=0;i<m_height+2;i++){
		delete tempInputImage[i];
	}
	delete tempInputImage;
	//////////////////////////////////////

	//0 ~ 255로 처리
	for(i=0; i<m_Re_height; i++) {
		for(j=0; j<m_Re_width; j++) {
			if(tempOutputImage[i][j] > 255.0) tempOutputImage[i][j] = 255.0;
			if(tempOutputImage[i][j] < 0.0) tempOutputImage[i][j] = 0.0;
		}
	}

	//tempOutputImage -->  m_OutputImage 
	for(i=0; i<m_Re_height; i++) 
		for(j=0; j<m_Re_width; j++) 
			m_OutputImage[i][j] = (unsigned char)tempOutputImage[i][j];


	//메모리 해제 
	for(i=0;i<m_Re_height;i++){
		delete tempOutputImage[i];
	}
	delete tempOutputImage;
}


double CFinalProjectDoc::FindMINValue2(double** InImage, int x, int y)
{
	int n,m;
	double result = 0.0 , sum=0.0;
	int index = 0;
	double tempArray[9] = {0.0}; //정렬할 배열 선언, 초기화
	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			//tempArray[(3*n)+m] = InImage[n+x][m+y];
			tempArray[index++] = InImage[n+x][m+y];
		}
	}
	//temp 가지고 버블소팅해서 가운데값 추출하고 
	for(int i=0; i<8;i++){
		for(int j=0; j<8-i;j++){
			if(tempArray[j] > tempArray[j+1]){
				int temp = tempArray[j];
				tempArray[j] = tempArray[j+1];
				tempArray[j+1] = temp; 
			}
		}
	}
	result = tempArray[0]; //최소값 선택

	//temp 초기화 시키고 
	for(n=0;n<3;n++){
		for(m=0;m<3;m++){
			tempArray[(3*n)+m] = 0.0;
		}
	}

	return result;
}

void CFinalProjectDoc::OnZoomInC()
{
	//칼라 이미지는 FinalProjectColor 에서 처리했습니다. 
}



